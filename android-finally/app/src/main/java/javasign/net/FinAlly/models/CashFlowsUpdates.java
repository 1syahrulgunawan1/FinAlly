package javasign.net.FinAlly.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CashFlowsUpdates {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Updates updates ;

    public class Updates {
        int id, product_id;
        String description, date, type, status, payment_flag, category;
        double amount, current_saldo;

        public Updates(int id, int product_id, String description, String date, String type, String status, String payment_flag, String category, double amount, double current_saldo) {
            this.id = id;
            this.product_id = product_id;
            this.description = description;
            this.date = date;
            this.type = type;
            this.status = status;
            this.payment_flag = payment_flag;
            this.category = category;
            this.amount = amount;
            this.current_saldo = current_saldo;
        }

        public int getId() {
            return id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getPayment_flag() {
            return payment_flag;
        }

        public String getCategory() {
            return category;
        }

        public double getAmount() {
            return amount;
        }

        public double getCurrent_saldo() {
            return current_saldo;
        }
    }


    public Updates getUpdates() {
        return updates;
    }

    public String getStatus() {
        return status;
    }
}
