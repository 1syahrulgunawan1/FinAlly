package javasign.net.FinAlly.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PromoDetailModels implements Parcelable {
    public String description, logo, due_date, name, ketentuan, lokasi, created_at, updated_at, deleted_at;
    public int ccpromos_id, id;

    protected PromoDetailModels(Parcel in) {
        description = in.readString();
        logo = in.readString();
        due_date = in.readString();
        name = in.readString();
        ketentuan = in.readString();
        lokasi = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        deleted_at = in.readString();
        ccpromos_id = in.readInt();
        id = in.readInt();
    }

    public static final Creator<PromoDetailModels> CREATOR = new Creator<PromoDetailModels>() {
        @Override
        public PromoDetailModels createFromParcel(Parcel in) {
            return new PromoDetailModels(in);
        }

        @Override
        public PromoDetailModels[] newArray(int size) {
            return new PromoDetailModels[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(logo);
        dest.writeString(due_date);
        dest.writeString(name);
        dest.writeString(ketentuan);
        dest.writeString(lokasi);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(deleted_at);
        dest.writeInt(ccpromos_id);
        dest.writeInt(id);
    }
}
