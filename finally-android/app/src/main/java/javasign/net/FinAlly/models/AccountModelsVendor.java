package javasign.net.FinAlly.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountModelsVendor implements Parcelable {
    public String id, name;

    protected AccountModelsVendor(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
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
