package by.nerohome.gateway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.nerohome.gateway.commands.OledoCommand;

public class OledoHostGateway implements Closeable {
    private static final String PROTOCOL = "TLSv1.2";
    private String host;
    private Integer port;
    private X509Certificate certificate;
    private SSLSocket socket;
    private PrintWriter writer;
    private ReaderThread readThread;

    public OledoHostGateway(
        String host,
        Integer port,
        X509Certificate certificate
    ){
        this.host = host;
        this.port = port;
        this.certificate = certificate;
    }

    public void connect() throws 
        NoSuchAlgorithmException,
        KeyManagementException,
        IOException {
        if (this.socket != null) {
            return;
        }

        this.socket = this.createSocket();
        this.writer = new PrintWriter(
            new BufferedWriter(
            new OutputStreamWriter(
            this.socket.getOutputStream())));
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
            this.socket.getInputStream()));
        this.readThread = new ReaderThread(reader);
        this.readThread.start();
    }

    public void send(OledoCommand command) throws IOException {
        StringWriter stringWriter = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(stringWriter, command);
        String s = stringWriter.toString();
        this.writer.print(s);
        this.writer.flush();
    }

    public void addReplyListener(ReplyListener listener) {
        this.readThread.addReplyListener(listener);
    }

    public void removeReplyListener(ReplyListener listener) {
        this.readThread.removeReplyListener(listener);
    }

    @Override
    public void close() throws IOException {
        if (this.writer != null) {
            this.writer.close();
        }

        if (this.readThread != null) {
            this.readThread.close();
            this.readThread.interrupt();
        }

        if (this.socket != null && !this.socket.isClosed()) {
            this.socket.close();
        }
    }

    private SSLSocket createSocket() throws
        NoSuchAlgorithmException,
        KeyManagementException,
        IOException {
        SSLContext sc = SSLContext.getInstance(PROTOCOL);
        sc.init(
            null,
            new TrustManager[] { new ExplicitTrustManager(this.certificate) },
            new java.security.SecureRandom());
        SSLSocketFactory factory = sc.getSocketFactory();
        socket = (SSLSocket)factory.createSocket(this.host, this.port);
        socket.startHandshake();

        return socket;
    }
}
