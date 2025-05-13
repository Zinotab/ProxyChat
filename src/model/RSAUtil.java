package model;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    private static Map<String, KeyPair> userKeyPairs = new HashMap<>();
    private static final int KEY_SIZE = 1024; 
    
    public static void generateKeyPair(String userId) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(KEY_SIZE);
            KeyPair keyPair = keyGen.generateKeyPair();
            userKeyPairs.put(userId, keyPair);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String encrypt(String plainText, String receiverId) {
        try {
            KeyPair receiverKeyPair = userKeyPairs.get(receiverId);
            if (receiverKeyPair == null) {
                return plainText; 
            }
            
            PublicKey publicKey = receiverKeyPair.getPublic();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return plainText;
        }
    }
    
    public static String decrypt(String encryptedText, String userId) {
        try {
            KeyPair userKeyPair = userKeyPairs.get(userId);
            if (userKeyPair == null) {
                return encryptedText; 
            }
            
            PrivateKey privateKey = userKeyPair.getPrivate();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "*** Encrypted Message ***"; 
        }
    }
    
    public static boolean hasKeyPair(String userId) {
        return userKeyPairs.containsKey(userId);
    }
    
    public static String getPublicKeyAsString(String userId) {
        try {
            KeyPair keyPair = userKeyPairs.get(userId);
            if (keyPair == null) return null;
            
            return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
