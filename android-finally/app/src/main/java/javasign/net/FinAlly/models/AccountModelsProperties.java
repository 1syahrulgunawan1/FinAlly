package javasign.net.FinAlly.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountModelsProperties implements Parcelable {
    public String cardholder;
    public String due_date;
    public String credit_limit;
    public String available_balance, openingBalance, newCharges, closingBalance;
    public String last_payment, customerNo, statementDate, cashAdvanceLimit, availableCashAdvanceLimit, cashAdvance, outstandingInstallment;
    public double minimum_payment;

    public AccountModelsProperties(String cardholder, String due_date, String credit_limit, double minimum_payment) {
        this.cardholder = cardholder;
        this.due_date = due_date;
        this.credit_limit = credit_limit;
        this.minimum_payment = minimum_payment;
    }

    public AccountModelsProperties(Parcel in) {
        cardholder = in.readString();
        due_date = in.readString();
        credit_limit = in.readString();
        available_balance = in.readString();
        openingBalance = in.readString();
        newCharges = in.readString();
        closingBalance = in.readString();
        last_payment = in.readString();
        customerNo = in.readString();
        statementDate = in.readString();
        cashAdvanceLimit = in.readString();
        availableCashAdvanceLimit = in.readString();
        cashAdvance = in.readString();
        outstandingInstallment = in.readString();
        minimum_payment = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardholder);
        dest.writeString(due_date);
        dest.writeString(credit_limit);
        dest.writeString(available_balance);
        dest.writeString(openingBalance);
        dest.writeString(newCharges);
        dest.writeString(closingBalance);
        dest.writeString(last_payment);
        dest.writeString(customerNo);
        dest.writeString(statementDate);
        dest.writeString(cashAdvanceLimit);
        dest.writeString(availableCashAdvanceLimit);
        dest.writeString(cashAdvance);
        dest.writeString(outstandingInstallment);
        dest.writeDouble(minimum_payment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountModelsProperties> CREATOR = new Creator<AccountModelsProperties>() {
        @Override
        public AccountModelsProperties createFromParcel(Parcel in) {
            return new AccountModelsProperties(in);
        }

        @Override
        public AccountModelsProperties[] newArray(int size) {
            return new AccountModelsProperties[size];
        }
    };
}
