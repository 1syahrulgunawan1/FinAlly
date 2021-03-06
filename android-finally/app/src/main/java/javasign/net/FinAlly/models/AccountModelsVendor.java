package javasign.net.FinAlly.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountModelsVendor implements Parcelable {
    public int id;
    public String name;

    public AccountModelsVendor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccountModelsVendor(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountModelsVendor> CREATOR = new Creator<AccountModelsVendor>() {
        @Override
        public AccountModelsVendor createFromParcel(Parcel in) {
            return new AccountModelsVendor(in);
        }

        @Override
        public AccountModelsVendor[] newArray(int size) {
            return new AccountModelsVendor[size];
        }
    };
}
