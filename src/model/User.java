package model;
public class User {
    private String id, pseudoName, password;

    public User(String id, String pseudoName, String password) {
        this.id = id;
        this.pseudoName = pseudoName;
        this.password = password;
    }


    public String getId() { return id; }
    public String getPseudoName() { return pseudoName; }
    public String getPassword() { return password; }
}