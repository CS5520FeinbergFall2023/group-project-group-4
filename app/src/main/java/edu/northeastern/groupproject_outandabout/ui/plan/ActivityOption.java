package edu.northeastern.groupproject_outandabout.ui.plan;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * This class represents an option presented to the user that they can select as part of their plan.
 * It encapsulates point of interest data from API calls so that the application
 * can use it for UI and plan building.
 */
public class ActivityOption implements Serializable, Parcelable {
    final String name;
    final String id;
    final String address;
    final String websiteUri;
    final float rating;
    private final String type;
    private boolean isSelected;

    public ActivityOption(String name, String id, String address, String websiteUri, float rating, String type) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.websiteUri = websiteUri;
        this.rating = rating;
        this.type = type;
        this.isSelected = false;
    }

    // Rest of your getters

    public String getType() { return this.type; }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    // Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(address);
        dest.writeString(websiteUri);
        dest.writeFloat(rating);
        dest.writeString(type);
        dest.writeByte((byte) (isSelected ? 1 : 0)); // Handling boolean for Parcelable
    }

    public static final Parcelable.Creator<ActivityOption> CREATOR = new Parcelable.Creator<ActivityOption>() {
        @Override
        public ActivityOption createFromParcel(Parcel in) {
            return new ActivityOption(in);
        }

        @Override
        public ActivityOption[] newArray(int size) {
            return new ActivityOption[size];
        }
    };

    private ActivityOption(Parcel in) {
        name = in.readString();
        id = in.readString();
        address = in.readString();
        websiteUri = in.readString();
        rating = in.readFloat();
        type = in.readString();
        isSelected = in.readByte() != 0; // Handling boolean for Parcelable
    }
}
