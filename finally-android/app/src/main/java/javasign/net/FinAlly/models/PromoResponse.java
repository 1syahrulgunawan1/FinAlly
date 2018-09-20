package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromoResponse {
    private @SerializedName("data")
    List<PromoModels> promoModels;

    public List<PromoModels> getPromoModels() {
        return promoModels;
    }

}
