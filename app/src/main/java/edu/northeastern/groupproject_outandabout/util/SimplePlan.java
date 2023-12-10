package edu.northeastern.groupproject_outandabout.util;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

// Simplified Plan class
public class SimplePlan {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private List<ActivityOption> selectedActivities;

    // Constructor
    public SimplePlan() {
        this.selectedActivities = new ArrayList<>();
    }

    // Getters and Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setSelectedActivities(List<ActivityOption> selectedActivities) { this.selectedActivities = selectedActivities; }

    public String getName() { return this.name;}

    public double getLatitude() { return this.latitude;
    }

    public double getLongitude() { return this.longitude;
    }

    public List<ActivityOption> getSelectedActivities() { return this.selectedActivities; }
}
