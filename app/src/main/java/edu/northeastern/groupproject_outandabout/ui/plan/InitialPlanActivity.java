package edu.northeastern.groupproject_outandabout.ui.plan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.SwipeActivity;

public class InitialPlanActivity extends AppCompatActivity {

    private RecyclerView initialPlanRecyclerView;
    private PlanSummaryAdapter adapter;
    private List<ActivityOption> selectedActivities;
    private static final int REQUEST_CODE_SWIPE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_plan);

        initialPlanRecyclerView = findViewById(R.id.initialPlanRecyclerView);
        initialPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize with one placeholder activity
        selectedActivities = new ArrayList<>();
        selectedActivities.add(new ActivityOption("Activity 1", "",0, ActivityType.NONE));

        adapter = new PlanSummaryAdapter(selectedActivities);
        initialPlanRecyclerView.setAdapter(adapter);

        setupAddActivitiesButton();
        setupFinalizePlanButton();
        setupGeneratePlanButton();
    }


    private void setupAddActivitiesButton() {
        FloatingActionButton addActivitiesButton = findViewById(R.id.addActivitiesButton);
        addActivitiesButton.setOnClickListener(v -> {
            // Add a new placeholder activity
            int newActivityNumber = selectedActivities.size() + 1;
            selectedActivities.add(new ActivityOption("Activity " + newActivityNumber, "", 0, ActivityType.NONE));
            adapter.notifyDataSetChanged();
        });
    }


    private void setupFinalizePlanButton() {
        Button finalizePlanButton = findViewById(R.id.finalizePlanButton);
        finalizePlanButton.setOnClickListener(v -> {
            // Create an AlertDialog.Builder
            new AlertDialog.Builder(InitialPlanActivity.this)
                    .setTitle("Finalize Plan") // Set Dialog Title
                    .setMessage("Are you sure you want to finalize this plan?") // Set the message

                    // Add the buttons
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // User clicked "Yes" button, navigate to PlanSummaryActivity
                        Intent intent = new Intent(InitialPlanActivity.this, PlanSummaryActivity.class);
                        intent.putExtra("selectedActivities", new ArrayList<>(adapter.getSelectedActivities()));
                        startActivity(intent);
                    })
                    .setNegativeButton(android.R.string.no, null) // No option will close dialogue box
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }


    private void setupGeneratePlanButton() {
        Button generatePlanButton = findViewById(R.id.generatePlanButton);
        generatePlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(InitialPlanActivity.this, SwipeActivity.class);

            // Collecting selected types and times
            ArrayList<String> selectedTypes = new ArrayList<>();
            ArrayList<String> selectedTimes = new ArrayList<>();
            for (ActivityOption option : selectedActivities) {
                selectedTypes.add(option.getSelectedType().name());
                selectedTimes.add(option.getSelectedTime());
            }

            intent.putStringArrayListExtra("selectedTypes", selectedTypes);
            intent.putStringArrayListExtra("selectedTimes", selectedTimes);

            startActivityForResult(intent, REQUEST_CODE_SWIPE_ACTIVITY);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SWIPE_ACTIVITY && resultCode == RESULT_OK) {
            ActivityOption selectedActivity = data.getParcelableExtra("SelectedActivity");
            if (selectedActivity != null) {
                // Update your activities list
                selectedActivities.set(0, selectedActivity);//Updating first
                adapter.notifyDataSetChanged();
            }
        }
    }
}
