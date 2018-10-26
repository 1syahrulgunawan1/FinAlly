package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<AccountModels> Account;

    public String getStatus() {
        return status;
    }

    public List<AccountModels> getAccount() {
        return Account;
    }

}
