package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CashFlowsResponse {
    @SerializedName("data")
    private List<CashFlowsModels> cashFlowsModels;

    public List<CashFlowsModels> getCashFlowsModels() {
        return cashFlowsModels;
    }
}
