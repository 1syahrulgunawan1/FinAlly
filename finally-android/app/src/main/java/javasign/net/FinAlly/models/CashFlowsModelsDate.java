package javasign.net.FinAlly.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CashFlowsModelsDate implements Parcelable {
    public String original, day;
    public int date, month, year;

    protected CashFlowsModelsDate(Parcel in) {
        original = in.readString();
        day = in.readString();
        date = in.readInt();
        month = in.readInt();
        year = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original);
        dest.writeString(day);
        dest.writeInt(date);
        dest.writeInt(month);
        dest.writeInt(year);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CashFlowsModelsDate> CREATOR = new Creator<CashFlowsModelsDate>() {
        @Override
        public CashFlowsModelsDate createFromParcel(Parcel in) {
            return new CashFlowsModelsDate(in);
        }

        @Override
        public CashFlowsModelsDate[] newArray(int size) {
            return new CashFlowsModelsDate[size];
        }
    };
}
