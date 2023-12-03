package edu.northeastern.groupproject_outandabout.ui.plan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.northeastern.groupproject_outandabout.R;

public class InitialPlanActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_ACTIVITY = 1; // Define a unique request code
    private RecyclerView initialPlanRecyclerView;
    private PlanSummaryAdapter adapter;
    private static List<ActivityOption> selectedActivities = new ArrayList<>();

    private ActivityResultLauncher<Intent> activityResultLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_plan);

        // Initialize the ActivityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        List<ActivityOption> newActivities = (List<ActivityOption>) result.getData().getSerializableExtra("addedActivities");
                        if (newActivities != null) {
                            selectedActivities.addAll(newActivities);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        initialPlanRecyclerView = findViewById(R.id.initialPlanRecyclerView);
        initialPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PlanSummaryAdapter(selectedActivities);
        initialPlanRecyclerView.setAdapter(adapter);

        findViewById(R.id.addActivitiesButton).setOnClickListener(v -> {
            Intent intent = new Intent(InitialPlanActivity.this, ActivitySelectionActivity.class);
            activityResultLauncher.launch(intent);
        });

        Button finalizePlanButton = findViewById(R.id.finalizePlanButton);
        finalizePlanButton.setOnClickListener(v -> {
            // Create an AlertDialog Builder
            new AlertDialog.Builder(InitialPlanActivity.this)
                    .setTitle("Confirm Finalization")
                    .setMessage("Are you sure you want to finalize your plan?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // User confirmed to finalize the plan

                        // Pass the selected activities to PlanSummaryActivity
                        Intent intent = new Intent(InitialPlanActivity.this, PlanSummaryActivity.class);
                        intent.putExtra("selectedActivities", new ArrayList<>(selectedActivities));
                        startActivity(intent);
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // User canceled, do nothing
                        dialog.dismiss();
                    })
                    .show();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ACTIVITY && resultCode == RESULT_OK && data != null) {
            List<ActivityOption> newActivities = (List<ActivityOption>) data.getSerializableExtra("selectedActivities");
            if (newActivities != null) {
                selectedActivities.addAll(newActivities);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public static void addActivitiesToInitialPlan(Set<ActivityOption> activitiesToAdd) {
        selectedActivities.addAll(activitiesToAdd);
    }


}
