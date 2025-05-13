package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Vector;

public class ParticipantListModel extends Observable {
    private Vector<User> participantList = new Vector<>();


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
    public void registerParticipant(String id, String pseudoName, String password) {
        String hashedPassword = hashPassword(password); 
        System.out.println("Registering user: " + pseudoName + " with hashed password: " + hashedPassword);
        participantList.add(new User(id, pseudoName, hashedPassword));
        setChanged();
        notifyObservers();
    }
    public User signIn(String pseudoName, String password) {
        String hashedPassword = hashPassword(password); 
        for (User user : participantList) {
            if (user.getPseudoName().equals(pseudoName) && user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        return null;
    }



    
    public User getUserById(String id) {
        for (User user : participantList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    public User getUserByPseudoName(String pseudoName) {
        for (User user : participantList) {
            if (user.getPseudoName().equals(pseudoName)) {
                return user;
            }
        }
        return null; 
    }
    public Vector<User> getParticipants() {
        return participantList;
    }
}