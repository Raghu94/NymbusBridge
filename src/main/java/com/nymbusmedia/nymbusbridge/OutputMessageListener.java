package com.nymbusmedia.nymbusbridge;

public interface OutputMessageListener {
    void onOutputMessageReceived(String message, String command);
}
