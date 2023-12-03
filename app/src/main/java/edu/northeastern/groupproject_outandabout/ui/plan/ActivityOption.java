package edu.northeastern.groupproject_outandabout.ui.plan;
import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/**
 * This class represents an option presented to the user that they can select as one of the as part
 * of their plan. It encapsulates point of interest data from API calls so that the application
 * can use it for UI and plan building.
 */
public class ActivityOption implements Serializable, Parcelable {

    final String name;
    final String id;
    final String address;
    final String websiteUri;
    final float rating;
    private boolean isSelected;

    public ActivityOption(String name, String id, String address, String websiteUri, float rating) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.websiteUri = websiteUri;
        this.rating = rating;
        this.isSelected = false;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }

    public String getWebsiteUri() {
        return this.websiteUri;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public float getRating() {
        return this.rating;
    }

    // Added this code to be able to pass a list of ActivityOptions as an intent extra
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
    }
}
