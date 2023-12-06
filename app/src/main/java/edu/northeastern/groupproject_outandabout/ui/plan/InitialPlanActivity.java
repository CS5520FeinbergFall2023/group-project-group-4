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
    private PlanSummaryAdapter adapter; // Update this adapter to work with ActivityBuilderSlot
    private List<ActivityBuilderSlot> plannedActivities; // Changed to ActivityBuilderSlot
    private static final int REQUEST_CODE_SWIPE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_plan);

        initialPlanRecyclerView = findViewById(R.id.initialPlanRecyclerView);
        initialPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        plannedActivities = new ArrayList<>();
        plannedActivities.add(new ActivityBuilderSlot(ActivityType.NONE, ""));
        adapter = new PlanSummaryAdapter(plannedActivities);
        initialPlanRecyclerView.setAdapter(adapter);

        setupAddActivitiesButton();
        //setupFinalizePlanButton();
        setupGeneratePlanButton();
    }


    private void setupAddActivitiesButton() {
        FloatingActionButton addActivitiesButton = findViewById(R.id.addActivitiesButton);
        addActivitiesButton.setOnClickListener(v -> {
            plannedActivities.add(new ActivityBuilderSlot(ActivityType.NONE, ""));
            adapter.notifyDataSetChanged();
        });
    }

//    private void setupFinalizePlanButton() {
//        Button finalizePlanButton = findViewById(R.id.finalizePlanButton);
//        finalizePlanButton.setOnClickListener(v -> {
//            new AlertDialog.Builder(InitialPlanActivity.this)
//                    .setTitle("Finalize Plan")
//                    .setMessage("Are you sure you want to finalize this plan?")
//                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
//                        Intent intent = new Intent(InitialPlanActivity.this, PlanSummaryActivity.class);
//                        intent.putExtra("plannedActivities", new ArrayList<>(adapter.getSelectedSlots()));
//                        startActivity(intent);
//                    })
//                    .setNegativeButton(android.R.string.no, null)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        });
//    }


    private void setupGeneratePlanButton() {
        Button generatePlanButton = findViewById(R.id.generatePlanButton);
        generatePlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(InitialPlanActivity.this, SwipeActivity.class);

            ArrayList<String> selectedTypes = new ArrayList<>();
            ArrayList<String> selectedTimes = new ArrayList<>();
            for (ActivityBuilderSlot slot : plannedActivities) {
                // Assuming getType() returns an ActivityType enum
                selectedTypes.add(slot.getType().toString());
                selectedTimes.add(slot.getTimeslot());
            }

            intent.putStringArrayListExtra("selectedTypes", selectedTypes);
            intent.putStringArrayListExtra("selectedTimes", selectedTimes);

            startActivityForResult(intent, REQUEST_CODE_SWIPE_ACTIVITY);
        });
    }

}
