package edu.northeastern.groupproject_outandabout;

import androidx.annotation.NonNull;

/**
 * This enum class represents the different category types of activities that the user can search and add
 * to their plans.
 */
public enum ActivityType {
    NONE,  // For no selection or a placeholder
    RESTAURANT,
    ENTERTAINMENT,
    NIGHTLIFE,
    OUTDOORS;

    @NonNull
    @Override
    public String toString() {
        switch(this) {
            case RESTAURANT:
                return "Restaurant";
            case ENTERTAINMENT:
                return "Entertainment";
            case NIGHTLIFE:
                return "Nightlife";
            case OUTDOORS:
                return "Outdoors";
            case NONE:
                return "";
            default:
                return "";
        }
    }
    public static ActivityType fromString(String typeString) {
        for (ActivityType type : ActivityType.values()) {
            if (type.toString().equalsIgnoreCase(typeString)) {
                return type;
            }
        }
        return NONE; // Default case
    }
}
