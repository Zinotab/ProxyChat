package model;

public class RealSubject {
    public void sendMessage(String userId, String pseudoNameReceiver, String message, MessageListModel messageModel) {
        messageModel.sendMessage(userId, pseudoNameReceiver, message);
    }
}