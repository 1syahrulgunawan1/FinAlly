package javasign.net.FinAlly.models;

public class VendorModels {
    private int id;
    private String name, alias_name, vendor_type, description, mode, instruction, label_username, label_password, login_url ;

    public VendorModels(int id, String name, String alias_name, String vendor_type, String description, String mode, String instruction, String label_username, String label_password, String login_url) {
        this.id = id;
        this.name = name;
        this.alias_name = alias_name;
        this.vendor_type = vendor_type;
        this.description = description;
        this.mode = mode;
        this.instruction = instruction;
        this.label_username = label_username;
        this.label_password = label_password;
        this.login_url = login_url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public String getVendor_type() {
        return vendor_type;
    }

    public String getDescription() {
        return description;
    }

    public String getMode() {
        return mode;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getLabel_username() {
        return label_username;
    }

    public String getLabel_password() {
        return label_password;
    }

    public String getLogin_url() {
        return login_url;
    }
}
