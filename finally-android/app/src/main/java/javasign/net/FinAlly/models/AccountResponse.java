package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountResponse {
    @SerializedName("data")
    private List<AccountModels> Account;

    public List<AccountModels> getAccount() {
        return Account;
    }

}
