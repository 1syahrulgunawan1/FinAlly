package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromoResponse {


    private @SerializedName("status")
    String status;

    private @SerializedName("data")
    List<PromoModels> promoModels;

    public String getStatus() {
        return status;
    }

    public List<PromoModels> getPromoModels() {
        return promoModels;
    }


}
