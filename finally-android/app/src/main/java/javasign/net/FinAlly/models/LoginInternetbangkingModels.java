package javasign.net.FinAlly.models;

public class LoginInternetbangkingModels {

    private VendorModels vendor;

    private String usernama, password, additional;

    public LoginInternetbangkingModels(VendorModels vendor, String usernama, String password, String additional) {
        this.vendor = vendor;
        this.usernama = usernama;
        this.password = password;
        this.additional = additional;
    }

    public VendorModels getVendor() {
        return vendor;
    }

    public String getUsernama() {
        return usernama;
    }

    public String getPassword() {
        return password;
    }

    public String getAdditional() {
        return additional;
    }
}
