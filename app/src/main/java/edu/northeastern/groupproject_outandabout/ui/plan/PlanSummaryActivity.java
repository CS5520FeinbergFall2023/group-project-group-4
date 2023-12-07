package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import edu.northeastern.groupproject_outandabout.R;

public class PlanSummaryActivity extends AppCompatActivity {

    private RecyclerView planSummaryRecyclerView;
    private ActivitySummaryAdapter adapter;  // Change to ActivitySummaryAdapter
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

        currentPlan = getPlan();
        adapter = new ActivitySummaryAdapter(currentPlan.getSelectedActivities());  // Use ActivitySummaryAdapter
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
        // Logic needed to save plan to database
    }

    private void exportPlan() {
        // Logic needed to export plan
    }

    private Plan getPlan() {
        // Retrieve the Plan object from the Intent
        Plan plan = (Plan) getIntent().getSerializableExtra("Plan");
        return plan != null ? plan : new Plan(); // Return a new Plan if none was passed
    }
}
