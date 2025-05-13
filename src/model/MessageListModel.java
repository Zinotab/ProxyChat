package model;

import java.util.Observable;
import java.util.Vector;

public class MessageListModel extends Observable {
    private Vector<Message> messageList = new Vector<>();
    private boolean useEncryption = false;

    public void toggleEncryption(boolean enable) {
        this.useEncryption = enable;
    }
    public boolean isEncryptionEnabled() {
        return this.useEncryption;
    }

    public void sendMessage(String sender, String receiver, String content) {
        String processedContent = content;
        boolean isEncrypted = false;
        
        
        if (useEncryption && RSAUtil.hasKeyPair(sender) && RSAUtil.hasKeyPair(receiver)) {
            processedContent = RSAUtil.encrypt(content, receiver);
            isEncrypted = true;
        }
        
        addMessage(new Message(messageList.size(), sender, receiver, processedContent, isEncrypted)); 
    }

    public void addMessage(Message message) {
        System.out.println("MessageListModel: Adding message: " + message.getContent());
        messageList.add(message); 
        setChanged();
        notifyObservers();
        System.out.println("MessageListModel: Observers notified.");
    }

    public Vector<Message> getMessagesForUser(String userId) {
        Vector<Message> userMessages = new Vector<>();
        for (Message msg : messageList) {
            if (msg.getReceiver().equals(userId) || msg.getSender().equals(userId)) {
                
                
                if (msg.isEncrypted() && msg.getReceiver().equals(userId)) {
                    String decryptedContent = RSAUtil.decrypt(msg.getContent(), userId);
                    userMessages.add(new Message(
                        msg.getMessageId(), 
                        msg.getSender(), 
                        msg.getReceiver(), 
                        decryptedContent
                    ));
                } else {
                    userMessages.add(msg);
                }
            }
        }
        return userMessages;
    }
}