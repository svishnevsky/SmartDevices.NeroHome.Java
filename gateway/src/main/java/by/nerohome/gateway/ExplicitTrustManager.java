package by.nerohome.gateway;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class ExplicitTrustManager implements X509TrustManager {
    private X509Certificate certificate;

    public ExplicitTrustManager(X509Certificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public void checkClientTrusted(
        X509Certificate[] chain,
        String authType) throws CertificateException {
        throw new UnsupportedOperationException(
            "This implementation should be used for server certificate validation only.");
    }

    @Override
    public void checkServerTrusted(
        X509Certificate[] chain,
        String authType) throws CertificateException {
        for (X509Certificate cert : chain) {
            try {
                cert.verify(this.certificate.getPublicKey());
            } catch (InvalidKeyException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                SignatureException e) {
                e.printStackTrace();
                throw new CertificateException(e.getMessage());
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
    
}
