package model;

public class Message {
    private int messageId;
    private String sender;
    private String receiver;
    private String content;
    private boolean encrypted;

    public Message(int messageId, String sender, String receiver, String content) {
        this(messageId, sender, receiver, content, false);
    }
    
    public Message(int messageId, String sender, String receiver, String content, boolean encrypted) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.encrypted = encrypted;
    }

    public int getMessageId() { return messageId; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getContent() { return content; }
    public boolean isEncrypted() { return encrypted; }
}