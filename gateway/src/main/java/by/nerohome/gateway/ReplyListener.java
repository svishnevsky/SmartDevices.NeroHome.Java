package by.nerohome.gateway;

import java.util.EventListener;

public interface ReplyListener extends EventListener {
    void onReply(String reply);
}
