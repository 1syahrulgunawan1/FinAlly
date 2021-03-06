package javasign.net.FinAlly.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AccountModelsProducts implements Parcelable {
    public int id;
    public String nickname, number, type;
    public double balance;
    public AccountModelsProperties properties;
    public AccountModelsVendor vendorModels;

    public AccountModelsProducts(String nickname, String type, double balance, AccountModelsProperties properties) {
        this.nickname = nickname;
        this.type = type;
        this.balance = balance;
        this.properties = properties;
    }

    public int idAccountModels;
    public AccountModelsProducts(Parcel in) {
        id = in.readInt();
        nickname = in.readString();
        number = in.readString();
        type = in.readString();
        balance = in.readDouble();
        properties = in.readParcelable(AccountModelsProperties.class.getClassLoader());
        vendorModels = in.readParcelable(AccountModelsVendor.class.getClassLoader());
        idAccountModels = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nickname);
        dest.writeString(number);
        dest.writeString(type);
        dest.writeDouble(balance);
        dest.writeParcelable(properties, flags);
        dest.writeParcelable(vendorModels, flags);
        dest.writeInt(idAccountModels);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountModelsProducts> CREATOR = new Creator<AccountModelsProducts>() {
        @Override
        public AccountModelsProducts createFromParcel(Parcel in) {
            return new AccountModelsProducts(in);
        }

        @Override
        public AccountModelsProducts[] newArray(int size) {
            return new AccountModelsProducts[size];
        }
    };

    public AccountModelsProperties getProperties() {
        return properties;
    }

    public void setVendorModels(AccountModelsVendor vendorModels) {
        this.vendorModels = vendorModels;
    }
}
