package edu.northeastern.groupproject_outandabout.ui.plan;

import edu.northeastern.groupproject_outandabout.ActivityType;

/**
 * This class represents a generic activity slot that will be used by the user to build and outline
 * their generic plan itinerary.
 */
public class ActivityBuilderSlot {

    private ActivityType type;
    private String timeslot; // May have to change when we determine how we're keeping track of timeslot

    public ActivityBuilderSlot(ActivityType type, String timeslot) {
        this.type = type;
        this.timeslot = timeslot;
    }

    public ActivityType getType() { return this.type; }
    public String getTimeslot() { return this.timeslot; }

    public void setType(ActivityType type) { this.type = type; }
    public void setTimeslot(String timeslot) { this.timeslot = timeslot; }
}
