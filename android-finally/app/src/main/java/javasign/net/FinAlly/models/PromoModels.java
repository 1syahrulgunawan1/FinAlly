package javasign.net.FinAlly.models;

import java.util.ArrayList;

public class PromoModels {
    private int id, vendor_id;

    public String category, image;

    public ArrayList<PromoDetailModels> promo_details;

    public PromoModels(int id, int vendor_id, String category, String image) {
        this.id = id;
        this.vendor_id = vendor_id;
        this.category = category;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<PromoDetailModels> getPromo_details() {
        return promo_details;
    }
}
