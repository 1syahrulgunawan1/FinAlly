package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

public class PasswordResponse {
    private @SerializedName("data")
    User user;

    public User getUser() {
        return user;
    }

}
