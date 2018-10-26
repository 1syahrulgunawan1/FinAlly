package javasign.net.FinAlly;

public class CashFlow {

    public String account_key, documentId;
    public int id, account_id, vendor_id, product_id;
    public String category, description, type, payment_flag, tags, note;
    public int amount;
    public Date date;
    public Location location;

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public static class Location {
        public String placename, address, lat, lang;

        public Location() {

        }

        public Location(String placename, String address, String lat, String lang) {
            this.placename = placename;
            this.address = address;
            this.lat = lat;
            this.lang = lang;
        }

        public String getAddress() {
            return address;
        }

        public String getLang() {
            return lang;
        }

        public String getLat() {
            return lat;
        }

        public String getPlacename() {
            return placename;
        }

    }

    public CashFlow() {

    }

    public CashFlow(int account_id, int vendor_id, String category, String description, String type, String payment_flag, String tags, String note, int amount, Date date, Location location) {
        this.account_id = account_id;
        this.vendor_id = vendor_id;
        this.category = category;
        this.description = description;
        this.type = type;
        this.payment_flag = payment_flag;
        this.tags = tags;
        this.note = note;
        this.amount = amount;
        this.date = date;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getPayment_flag() {
        return payment_flag;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getAccount_key() {
        return account_key;
    }

    public String getNote() {
        return note;
    }

    public String getTags() {
        return tags;
    }

    public Date getDate() {
        return date;
    }

    public static class Date {

        public String original, day;

        public Date() {

        }

        public Date(String original, String day) {
            this.original = original;
            this.day = day;
        }

        public String getDay() {
            return day;
        }

        public String getOriginal() {
            return original;
        }

    }
}