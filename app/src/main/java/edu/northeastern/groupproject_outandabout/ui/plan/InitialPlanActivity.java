package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.SwipeActivity;
import edu.northeastern.groupproject_outandabout.api.GooglePlacesCaller;

public class InitialPlanActivity extends AppCompatActivity {

    private RecyclerView initialPlanRecyclerView;
    private PlanSummaryAdapter adapter;
    private List<ActivityBuilderSlot> plannedActivities;
    private EditText locationInputEditText;
    private boolean isInputLocation;

    private Handler threadHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_plan);

        initialPlanRecyclerView = findViewById(R.id.initialPlanRecyclerView);
        locationInputEditText = findViewById(R.id.locationInputEditText);
        isInputLocation = getIntent().getBooleanExtra("inputLocation", false);

        initialPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        plannedActivities = new ArrayList<>();
        plannedActivities.add(new ActivityBuilderSlot(ActivityType.NONE, "", "AM"));
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
            plannedActivities.add(new ActivityBuilderSlot(ActivityType.NONE, "", "AM"));
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
            // User clicks but hasn't entered a location
            if (isInputLocation && locationInputEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(InitialPlanActivity.this, "Please enter a location", Toast.LENGTH_LONG).show();
                return;
            }

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

                Intent intent = new Intent(InitialPlanActivity.this, PreSwipeActivity.class);
                intent.putExtra("Plan", plan);
                startActivity(intent);
            }
            else if (isInputLocation) {
                String inputLocation = locationInputEditText.getText().toString();
                plan.setIsInputLocation(true);

                // Geocode User input location
                Thread thread = new Thread(new GeocodingRunnable(plan, inputLocation));
                thread.start();
            }
        });
    }

    private void handleLocationInput() {
        if (isInputLocation) {
            locationInputEditText.setVisibility(View.VISIBLE);
        }
    }

    class GeocodingRunnable implements Runnable {
        private Plan plan;
        private String address;

        public GeocodingRunnable(Plan currPlan, String inputLocation) {
            plan = currPlan;
            address = inputLocation;
        }
        @Override
        public void run() {
            // Geocode address and add lat/long to plan. Then move on to Preswipe
            double[] latLong = GooglePlacesCaller.geocodeAddress(address);
            // If geocode returned a result, move forward
            if (latLong[0] > 0) {
                plan.setLatitude(latLong[1]);
                plan.setLongitude(latLong[2]);

                Intent intent = new Intent(InitialPlanActivity.this, PreSwipeActivity.class);
                intent.putExtra("Plan", plan);
                threadHandler.post(() -> startActivity(intent));
            }
            // If no results display message
            else {
                threadHandler.post(() -> Toast.makeText(InitialPlanActivity.this, "No results for entered location", Toast.LENGTH_LONG).show());
            }
        }
    }
}
