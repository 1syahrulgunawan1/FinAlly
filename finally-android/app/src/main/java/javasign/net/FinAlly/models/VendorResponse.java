package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorResponse {
    @SerializedName("data")
    private List<VendorModels> itemvendor;

    public List<VendorModels> getItemvendor(){
        return itemvendor;
    }
}
