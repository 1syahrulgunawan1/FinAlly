package javasign.net.FinAlly.models;

public class User {

    private int id;
    private String email, username, access_token;

    public User(int id, String email, String username, String access_token) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.access_token = access_token;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAccess_token() {
        return access_token;
    }
}
