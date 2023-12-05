package edu.northeastern.groupproject_outandabout.ui.plan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Create a list of placeholder activities
        selectedActivities = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            selectedActivities.add(new ActivityOption("Activity " + i, "", "", "", 0, ActivityType.NONE));
        }

        adapter = new PlanSummaryAdapter(selectedActivities);
        initialPlanRecyclerView.setAdapter(adapter);

        findViewById(R.id.addActivitiesButton).setOnClickListener(v -> {
            // Get the selected type and time from the adapter
            String selectedType = String.valueOf(adapter.getSelectedType());
            String selectedTime = adapter.getSelectedTime();

            Intent intent = new Intent(InitialPlanActivity.this, SwipeActivity.class);

            // Pass the selected type and time as extras
            intent.putExtra("activityType", selectedType);
            intent.putExtra("activityTime", selectedTime);

            startActivityForResult(intent, REQUEST_CODE_SWIPE_ACTIVITY);
        });

        setupFinalizePlanButton();
    }


    private void setupFinalizePlanButton() {
        Button finalizePlanButton = findViewById(R.id.finalizePlanButton);
        finalizePlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(InitialPlanActivity.this, PlanSummaryActivity.class);
            intent.putExtra("selectedActivities", new ArrayList<>(adapter.getSelectedActivities()));  // Pass selected activities
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SWIPE_ACTIVITY && resultCode == RESULT_OK) {
            ActivityOption selectedActivity = data.getParcelableExtra("SelectedActivity");
            if (selectedActivity != null) {
                adapter.addActivity(selectedActivity);  // Add the selected activity to the adapter
                adapter.notifyDataSetChanged();
            }
        }
    }
}
