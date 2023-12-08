package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.util.FirebaseDatabaseUtil;
import edu.northeastern.groupproject_outandabout.util.PlanExportUtil;

public class PlanSummaryActivity extends AppCompatActivity {

    private RecyclerView planSummaryRecyclerView;
    private ActivitySummaryAdapter adapter;
    private Button savePlanButton;
    private Button exportPlanButton;
    private Button regeneratePlanButton;
    private Plan currentPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_summary);

        planSummaryRecyclerView = findViewById(R.id.planSummaryRecyclerView);
        planSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        savePlanButton = findViewById(R.id.savePlanButton);
        exportPlanButton = findViewById(R.id.exportPlanButton);
        regeneratePlanButton = findViewById(R.id.regeneratePlanButton);

        // Check if user is logged in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // show the save button
            savePlanButton.setVisibility(View.VISIBLE);
        } else {
            // hide the save button
            savePlanButton.setVisibility(View.GONE);
        }

        currentPlan = getPlan();
        adapter = new ActivitySummaryAdapter(currentPlan.getSelectedActivities());
        planSummaryRecyclerView.setAdapter(adapter);

        setupButtons();
    }

    private void setupButtons() {
        savePlanButton.setOnClickListener(v -> {
            // Implement the save functionality
            savePlan();
        });

        exportPlanButton.setOnClickListener(v -> {
            // Implement the export functionality
            exportPlan();
        });

        regeneratePlanButton.setOnClickListener(v -> {
            // Go back to InitialPlanActivity
            Intent intent = new Intent(this, InitialPlanActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        });
    }

    private void savePlan() {
        FirebaseDatabaseUtil firebaseDbUtil = new FirebaseDatabaseUtil();
        firebaseDbUtil.savePlan(currentPlan, new FirebaseDatabaseUtil.DatabaseOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(PlanSummaryActivity.this, "New plan saved successfully!", Toast.LENGTH_SHORT).show();
                savePlanButton.setEnabled(false);

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(PlanSummaryActivity.this, "Failed to save new plan: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exportPlan() {
        String planDetails = PlanExportUtil.convertPlanToText(currentPlan);
        PlanExportUtil.sharePlan(this, planDetails);    }

    private Plan getPlan() {
        // Retrieve the Plan object from the Intent
        Plan plan = (Plan) getIntent().getSerializableExtra("Plan");
        return plan != null ? plan : new Plan(); // Return a new Plan if none was passed
    }
}
