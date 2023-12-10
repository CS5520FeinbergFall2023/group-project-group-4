package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;

public class InitialPlanActivity extends AppCompatActivity {

    private RecyclerView initialPlanRecyclerView;
    private PlanSummaryAdapter adapter;
    private List<ActivityBuilderSlot> plannedActivities;
    private EditText locationInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_plan);

        initialPlanRecyclerView = findViewById(R.id.initialPlanRecyclerView);
        locationInputEditText = findViewById(R.id.locationInputEditText);

        initialPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        plannedActivities = new ArrayList<>();
        plannedActivities.add(new ActivityBuilderSlot(ActivityType.NONE, ""));
        adapter = new PlanSummaryAdapter(plannedActivities);
        initialPlanRecyclerView.setAdapter(adapter);

        setupAddActivitiesButton();
        setupRemoveActivitiesButton();
        setupGeneratePlanButton();
        handleLocationInput();
    }

    private void setupAddActivitiesButton() {
        FloatingActionButton addActivitiesButton = findViewById(R.id.addActivitiesButton);
        addActivitiesButton.setOnClickListener(v -> {
            plannedActivities.add(new ActivityBuilderSlot(ActivityType.NONE, ""));
            adapter.notifyDataSetChanged();
        });
    }

    private void setupRemoveActivitiesButton() {
        FloatingActionButton removeActivitiesButton = findViewById(R.id.removeActivitiesButton);
        removeActivitiesButton.setOnClickListener(v -> {
            if (plannedActivities.size() > 1) {
                plannedActivities.remove(plannedActivities.size() - 1);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupGeneratePlanButton() {
        Button generatePlanButton = findViewById(R.id.generatePlanButton);
        generatePlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(InitialPlanActivity.this, PreSwipeActivity.class);
            Plan plan = new Plan();
            for (ActivityBuilderSlot slot : plannedActivities) {
                plan.addActivitySlot(slot);
            }

            // Retrieve location data if available
            if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
                double latitude = getIntent().getDoubleExtra("latitude", 0);
                double longitude = getIntent().getDoubleExtra("longitude", 0);
                plan.setLatitude(latitude);
                plan.setLongitude(longitude);
            } else if (getIntent().getBooleanExtra("inputLocation", false)) {
                String inputLocation = locationInputEditText.getText().toString();
                plan.setInputLocation(inputLocation); // Add a field and a setter for inputLocation in Plan class
            }

            intent.putExtra("Plan", plan);
            startActivity(intent);
        });
    }

    private void handleLocationInput() {
        // Showing the location input field if user is expected to input a location
        if (getIntent().getBooleanExtra("inputLocation", false)) {
            locationInputEditText.setVisibility(View.VISIBLE);
        }
    }
}
