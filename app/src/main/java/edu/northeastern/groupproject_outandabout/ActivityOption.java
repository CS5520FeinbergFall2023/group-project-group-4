package edu.northeastern.groupproject_outandabout;

/**
 * This class represents an option presented to the user that they can select as one of the as part
 * of their plan. It encapsulates point of interest data from API calls so that the application
 * can use it for UI and plan building.
 */
public class ActivityOption {

    final String name;
    final String id;
    final String address;
    final String websiteUri;
    final float rating;

    public ActivityOption(String name, String id, String address, String websiteUri, float rating) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.websiteUri = websiteUri;
        this.rating = rating;
    }

    public String getName() { return this.name; }

    public String getId() { return this.id; }

    public String getAddress() { return this.address; }

    public String getWebsiteUri() { return this.websiteUri; }

    public float getRating() { return this.rating; }

}
