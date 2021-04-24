package by.nerohome.gateway;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class ReaderThread extends Thread implements Closeable {
    private static final int CLOSED_BRACKET = 125;
    private BufferedReader reader;
    private Collection<ReplyListener> listeners = new HashSet<>();

    public ReaderThread(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        while(!this.isInterrupted()) {
            StringBuilder currentReply = new StringBuilder();
            int currentSymbol = 0;
            do {
                try {
                    currentSymbol = this.reader.read();
                    currentReply.append((char)currentSymbol);
                } catch(IOException exception) {}
            } while(currentSymbol != CLOSED_BRACKET);

            for (ReplyListener replyListener : listeners) {
                replyListener.onReply(currentReply.toString());
            }
        }
    }

    public void addReplyListener(ReplyListener listener) {
        this.listeners.add(listener);
    }

    public void removeReplyListener(ReplyListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void close() throws IOException {
        if (this.reader != null) {
            this.reader.close();
        }
    }
}
