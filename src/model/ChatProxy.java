package model;

public class ChatProxy extends RealSubject implements Subject {
    private String userId;
    
    public ChatProxy() {
    }
    
    public void request(String userId) {
        this.userId = userId;
    }
    
    public void sendMessage(String pseudoNameReceiver, String message, MessageListModel messageModel) {
        super.sendMessage("Anonymous", pseudoNameReceiver, message, messageModel);
        System.out.println("Message sent via proxy from user: " + userId);
    }
}