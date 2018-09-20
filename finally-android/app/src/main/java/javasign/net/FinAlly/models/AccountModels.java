package javasign.net.FinAlly.models;

import java.util.List;

public class AccountModels {
    public int id;
    public String nickname;

    public AccountModelsVendor vendor;
    public List<AccountModelsProducts> products;

    public AccountModels() {
        this.id = id;
        this.nickname = nickname;
        this.vendor = vendor;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public AccountModelsVendor getVendor() {
        return vendor;
    }

    public List<AccountModelsProducts> getProducts() {
        return products;
    }
}
