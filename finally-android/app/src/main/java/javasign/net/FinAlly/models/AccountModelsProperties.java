package javasign.net.FinAlly.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountModelsProperties implements Parcelable {
    public String cardholder;
    public String due_date;
    public String credit_limit;
    public double available_balance;
    public double last_payment;
    double minimum_payment;

    protected AccountModelsProperties(Parcel in) {
        cardholder = in.readString();
        due_date = in.readString();
        credit_limit = in.readString();
        available_balance = in.readDouble();
        last_payment = in.readDouble();
        minimum_payment = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardholder);
        dest.writeString(due_date);
        dest.writeString(credit_limit);
        dest.writeDouble(available_balance);
        dest.writeDouble(last_payment);
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
