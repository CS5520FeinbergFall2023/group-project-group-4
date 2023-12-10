package edu.northeastern.groupproject_outandabout.ui.plan;

import java.io.Serializable;

import edu.northeastern.groupproject_outandabout.ActivityType;

/**
 * This class represents a generic activity slot that will be used by the user to build and outline
 * their generic plan itinerary.
 */
public class ActivityBuilderSlot implements Serializable {

    private ActivityType type;
    private String timeslot;
    private String ampm;

    public ActivityBuilderSlot(ActivityType type, String timeslot, String ampm) {
        this.type = type;
        this.timeslot = timeslot;
        this.ampm = ampm;
    }

    @Override
    public String toString() {
        return "Type: " + (type != null ? type.name() : "N/A") +
                ", Timeslot: " + (timeslot != null ? timeslot : "N/A");
    }

    public ActivityType getType() { return this.type; }
    public String getTimeslot() { return this.timeslot; }

    public void setType(ActivityType type) { this.type = type; }
    public void setTimeslot(String timeslot) { this.timeslot = timeslot; }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public String getCompleteTime() { return this.timeslot + this.ampm; }
}
