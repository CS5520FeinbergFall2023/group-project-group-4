package edu.northeastern.groupproject_outandabout.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.Plan;

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

        databaseReference.child("users").child(userId).child("plans").child(planId).setValue(plan)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
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
                List<Plan> plans = new ArrayList<>();
                for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                    Plan plan = planSnapshot.getValue(Plan.class);
                    if (plan != null) {
                        plans.add(plan);
                    }
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
        void onSuccess(List<Plan> plans);
        void onFailure(Exception e);
    }



    public interface DatabaseOperationCallback {
        void onSuccess();
        void onFailure(String error);
    }
}