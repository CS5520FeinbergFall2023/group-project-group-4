package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import edu.northeastern.groupproject_outandabout.R;

import java.util.ArrayList;
import java.util.List;

public class PlanSummaryActivity extends AppCompatActivity {

    private RecyclerView planSummaryRecyclerView;
    private PlanSummaryAdapter adapter;
    private Button savePlanButton;
    private Button exportPlanButton;
    private Button regeneratePlanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_summary);

        planSummaryRecyclerView = findViewById(R.id.planSummaryRecyclerView);
        planSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        savePlanButton = findViewById(R.id.savePlanButton);
        exportPlanButton = findViewById(R.id.exportPlanButton);
        regeneratePlanButton = findViewById(R.id.regeneratePlanButton);

        List<ActivityOption> selectedActivities = getSelectedActivities();
        adapter = new PlanSummaryAdapter(selectedActivities);
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
        // Logic need to save plan to database
    }

    private void exportPlan() {
        // Logic need to export plan here
    }

    private List<ActivityOption> getSelectedActivities() {
        // Retrieve the list of selected activities from the Intent
        List<ActivityOption> selectedActivities = (List<ActivityOption>) getIntent().getSerializableExtra("selectedActivities");
        return selectedActivities != null ? selectedActivities : new ArrayList<>();
    }
}
