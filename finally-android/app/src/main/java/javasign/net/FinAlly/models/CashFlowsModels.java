package javasign.net.FinAlly.models;

public class CashFlowsModels {
    public int id, account_id, product_id;
    public double amount;
    public String category, description, type;
    public CashFlowsModelsDate date;

    public CashFlowsModels(int id, int account_id, int product_id, int amount, String category, String description, String type, CashFlowsModelsDate date) {
        this.id = id;
        this.account_id = account_id;
        this.product_id = product_id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.type = type;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public CashFlowsModelsDate getDate() {
        return date;
    }
}
