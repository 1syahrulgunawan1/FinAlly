package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CashFlowsResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<CashFlowsModels> cashFlowsModels;

    public String getStatus() {
        return status;
    }

    public List<CashFlowsModels> getCashFlowsModels() {
        return cashFlowsModels;
    }
}
