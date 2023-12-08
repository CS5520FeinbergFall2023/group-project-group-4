package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.Plan;
import edu.northeastern.groupproject_outandabout.ui.plan.PlanListAdapter;
import edu.northeastern.groupproject_outandabout.util.FirebaseDatabaseUtil;

// ... other imports

public class UserAccountActivity extends AppCompatActivity {

    private RecyclerView plansRecyclerView;
    private PlanListAdapter planListAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        TextView userEmailTextView = findViewById(R.id.userEmailTextView);
        plansRecyclerView = findViewById(R.id.plansRecyclerView); // Add this line

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userEmailTextView.setText(currentUser.getEmail());
            userEmailTextView.setVisibility(View.VISIBLE);
            loadUserPlans(); // Load plans for the user
        } else {
            userEmailTextView.setText("No user is signed in.");
        }
    }

    private void loadUserPlans() {
        FirebaseDatabaseUtil firebaseDbUtil = new FirebaseDatabaseUtil();
        firebaseDbUtil.fetchPlansForUser(new FirebaseDatabaseUtil.DatabaseReadCallback() {
            @Override
            public void onSuccess(List<Plan> plans) {
                planListAdapter = new PlanListAdapter(plans, plan -> {
                    // Handle plan click event here
                    // For example, start a new activity with the plan details
                });
                plansRecyclerView.setLayoutManager(new LinearLayoutManager(UserAccountActivity.this));
                plansRecyclerView.setAdapter(planListAdapter);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle the error
            }
        });
    }
}
