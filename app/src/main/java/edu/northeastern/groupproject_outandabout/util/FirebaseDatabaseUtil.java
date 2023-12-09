package edu.northeastern.groupproject_outandabout.util;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;
import edu.northeastern.groupproject_outandabout.ui.plan.Plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDatabaseUtil {

    private final DatabaseReference databaseReference;

    public FirebaseDatabaseUtil() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public void savePlan(Plan plan, DatabaseOperationCallback callback) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            callback.onFailure("User not logged in");
            return;
        }

        String userId = currentUser.getUid();
        String planId = databaseReference.child("users").child(userId).child("plans").push().getKey();
        plan.setId(planId);

        Map<String, Object> planData = convertPlanToMap(plan);
        databaseReference.child("users").child(userId).child("plans").child(planId).setValue(planData)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    private Map<String, Object> convertPlanToMap(Plan plan) {
        Map<String, Object> map = new HashMap<>();
        map.put("Plan id", plan.getId());
        map.put("Plan name", plan.getName());
        map.put("Plan latitude", plan.getLatitude());
        map.put("Plan longitude", plan.getLongitude());

        // change serialized data into flat map
        List<Map<String, Object>> serializedOptions = new ArrayList<>();
        for (ActivityOption option : plan.getSelectedActivities()) {
            serializedOptions.add(convertActivityOptionToMap(option));
        }
        map.put("selectedActivities", serializedOptions);

        return map;
    }

    private Map<String, Object> convertActivityOptionToMap(ActivityOption option) {
        Map<String, Object> activityMap = new HashMap<>();
        activityMap.put("name", option.getName());
        activityMap.put("address", option.getAddress());
        activityMap.put("rating", option.getRating());
        activityMap.put("type", option.getSelectedType() != null ? option.getSelectedType().name() : null);
        activityMap.put("selectedTime", option.getSelectedTime());
        activityMap.put("isSelected", option.isSelected());
        return activityMap;
    }

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

    public void fetchPlansForUser(DatabaseReadCallback callback) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            callback.onFailure(new Exception("User not logged in"));
            return;
        }

        String userId = currentUser.getUid();
        databaseReference.child("users").child(userId).child("plans").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SimplePlan> plans = new ArrayList<>();
                for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                    SimplePlan plan = new SimplePlan();
                    plan.setId(planSnapshot.child("Plan id").getValue(String.class));
                    plan.setName(planSnapshot.child("Plan name").getValue(String.class));
                    Double latitude = planSnapshot.child("Plan latitude").getValue(Double.class);
                    Double longitude = planSnapshot.child("Plan longitude").getValue(Double.class);
                    plan.setLatitude(latitude != null ? latitude : 0);
                    plan.setLongitude(longitude != null ? longitude : 0);

                    // Log retrieved data
                    Log.d("FirebaseDebug", "Plan: " + plan.getName() + ", Lat: " + latitude + ", Long: " + longitude);

                    List<ActivityOption> activities = new ArrayList<>();
                    for (DataSnapshot activitySnapshot : planSnapshot.child("selectedActivities").getChildren()) {
                        ActivityOption activity = activitySnapshot.getValue(ActivityOption.class);
                        if (activity != null) {
                            activities.add(activity);
                            // Log activity details
                            Log.d("FirebaseDebug", "Activity: " + activity.getName() + ", Address: " + activity.getAddress());
                        }
                    }
                    plan.setSelectedActivities(activities);
                    plans.add(plan);
                }
                callback.onSuccess(plans);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    public interface DatabaseReadCallback {
        void onSuccess(List<SimplePlan> plans);
        void onFailure(Exception e);
    }

    public interface DatabaseOperationCallback {
        void onSuccess();
        void onFailure(String error);
    }
}
