package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;

/**
 * This class represents the plan that user is building throughout the app progression
 */
public class Plan implements Serializable {

    private String id;
    private String name;
    private ArrayList<ActivityBuilderSlot> activityBuilderSlots;
    private ArrayList<ActivityOption> selectedActivities;
    private double latitude;
    private double longitude;
    private boolean isInputLocation;

    public Plan() {

        this.name = "";
        this.activityBuilderSlots = new ArrayList<>();
        this.selectedActivities = new ArrayList<>();
        this.isInputLocation = false;
    }

    public String getName() { return this.name; }
    public ArrayList<ActivityBuilderSlot> getActivitySlots() { return this.activityBuilderSlots; }
    public ArrayList<ActivityOption> getSelectedActivities() { return this.selectedActivities; }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public void setIsInputLocation(boolean isInputLocation) { this.isInputLocation = isInputLocation; }
    public Boolean getIsInputLocation() { return isInputLocation; }

    public void setName(String name) { this.name = name; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void addActivitySlot(ActivityBuilderSlot slot) { this.activityBuilderSlots.add(slot); }
    public void addSelectedActivity(ActivityOption option) { this.selectedActivities.add(option); }
    public void removeActivitySlot(int index) { this.activityBuilderSlots.remove(index); }
    public void removeSelectedActivity(int index) { this.selectedActivities.remove(index); }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
