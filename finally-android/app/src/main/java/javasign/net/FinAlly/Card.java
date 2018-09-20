package javasign.net.FinAlly;

import com.google.firebase.firestore.Exclude;

public class Card {
    private String documentid;
    private String cardname;
    private String limit_credit;
    private String vendor;
    private String print_bills;
    private String due_date;
    private String interest;
    private String minimum_payment;
    private String type_card;

    public Card() {

    }

    public Card(String cardname, String limit_credit, String vendor, String print_bills, String due_date, String interest, String minimum_payment, String type_card) {
        this.cardname = cardname;
        this.limit_credit = limit_credit;
        this.vendor = vendor;
        this.print_bills = print_bills;
        this.due_date = due_date;
        this.interest = interest;
        this.minimum_payment = minimum_payment;
        this.type_card = type_card;
    }

    @Exclude
    public String getDocumentid() {
        return documentid;
    }

    public String getCardname() {
        return cardname;
    }

    public String getLimit_credit() {
        return limit_credit;
    }

    public String getVendor() {
        return vendor;
    }

    public String getPrint_bills() {
        return print_bills;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getInterest() {
        return interest;
    }

    public String getMinimum_payment() {
        return minimum_payment;
    }

    public String getType_card() {
        return type_card;
    }
}