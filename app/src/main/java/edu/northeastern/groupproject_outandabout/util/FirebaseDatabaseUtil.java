package edu.northeastern.groupproject_outandabout.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import edu.northeastern.groupproject_outandabout.ui.plan.Plan;

public class FirebaseDatabaseUtil {

    private final DatabaseReference databaseReference;

    public FirebaseDatabaseUtil() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public void savePlan(Plan plan, DatabaseOperationCallback callback) {


        String planId = databaseReference.child("plans").push().getKey();
        plan.setId(planId);

        databaseReference.child("plans").child(planId).setValue(plan)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }



    public interface DatabaseOperationCallback {
        void onSuccess();
        void onFailure(String error);
    }
}