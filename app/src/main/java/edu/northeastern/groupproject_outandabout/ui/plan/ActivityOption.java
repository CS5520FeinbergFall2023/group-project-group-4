package edu.northeastern.groupproject_outandabout.ui.plan;
import android.os.Parcel;
import android.os.Parcelable;
import edu.northeastern.groupproject_outandabout.ActivityType;
import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * This class represents an option presented to the user that they can select as part of their plan.
 * It encapsulates point of interest data from API calls so that the application
 * can use it for UI and plan building.
 */
public class ActivityOption implements Serializable, Parcelable {
    final String name;
    final String address;
    final String rating;
    private ActivityType type;
    private String selectedTime;
    private boolean isSelected;

    public ActivityOption(String name, String address, String rating, ActivityType type) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.type = type;
        this.isSelected = false;
        this.selectedTime = "";
    }

    // Getters and setters
    public String getName() { return this.name; }
    public String getAddress() { return this.address; }
    public String getRating() { return this.rating; }
    // Getter and setter for selected type
    public ActivityType getSelectedType() { return type; }
    public void setSelectedType(ActivityType selectedType) { type = selectedType; }
    // Getter and setter for selected time
    public String getSelectedTime() { return selectedTime; }
    public void setSelectedTime(String selectedTime) { this.selectedTime = selectedTime; }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    // Method to determine if this instance is a placeholder
    public boolean isPlaceholder() {
        return type == ActivityType.NONE || type == null;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(rating);
        dest.writeString(type != null ? type.name() : null);
        dest.writeString(selectedTime);
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
        address = in.readString();
        rating = in.readString();
        String typeString = in.readString();
        type = typeString != null ? ActivityType.valueOf(typeString) : null;
        selectedTime = in.readString();
        isSelected = in.readByte() != 0;
    }
}
