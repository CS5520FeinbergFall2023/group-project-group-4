package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;


import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.groupproject_outandabout.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityListActivity extends AppCompatActivity {

    private RecyclerView activitiesRecyclerView;
    private ActivityAdapter adapter;
    private Set<ActivityOption> selectedActivities = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupRecyclerView();
        setupButtons();
    }

    private void setupRecyclerView() {
        activitiesRecyclerView = findViewById(R.id.activitiesRecyclerView);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ActivityOption> filteredActivities = filterActivitiesBasedOnPreferences(getUserPreferences(), createMockActivities());
        adapter = new ActivityAdapter(filteredActivities);
        activitiesRecyclerView.setAdapter(adapter);
    }

    private ActivitySelectionActivity.UserPreferences getUserPreferences() {
        return (ActivitySelectionActivity.UserPreferences) getIntent().getSerializableExtra("UserPreferences");
    }

    private void addSelectedActivitiesToPlan() {
        Set<ActivityOption> activitiesToAdd = adapter.getSelectedActivities();
        selectedActivities.addAll(activitiesToAdd);
        Toast.makeText(this, "Activities added to plan", Toast.LENGTH_SHORT).show();

        // Log the size and contents of the selectedActivities set
        Log.d("ActivityListActivity", "Selected Activities Added: " + activitiesToAdd.size());
        for (ActivityOption activity : activitiesToAdd) {
            Log.d("ActivityListActivity", "Activity: " + activity.getName());
        }
    }

    private void setupButtons() {
        Button btnAddToPlan = findViewById(R.id.btnAddToPlan);
        btnAddToPlan.setOnClickListener(view -> addSelectedActivitiesToPlan());

        Button btnViewPlan = findViewById(R.id.btnViewPlan);
        btnViewPlan.setOnClickListener(view -> {
            // Log the size and contents of the selectedActivities set before sending
            Log.d("ActivityListActivity", "Preparing to view plan with Activities Count: " + selectedActivities.size());
            for (ActivityOption activity : selectedActivities) {
                Log.d("ActivityListActivity", "Sending Activity: " + activity.getName());
            }

            // Create an intent to start InitialPlanActivity
            Intent intent = new Intent(this, InitialPlanActivity.class);
            intent.putExtra("selectedActivities", new ArrayList<>(selectedActivities));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            // Start InitialPlanActivity with the intent
            startActivity(intent);
        });

    }



    private List<ActivityOption> filterActivitiesBasedOnPreferences(ActivitySelectionActivity.UserPreferences preferences, List<ActivityOption> allActivities) {
        if (preferences == null) return allActivities;
        List<ActivityOption> filteredActivities = new ArrayList<>();
        for (ActivityOption activity : allActivities) {
            if ((preferences.restaurant && activity.getType().equals("Restaurant")) ||
                    (preferences.entertainment && activity.getType().equals("Entertainment")) ||
                    (preferences.nightlife && activity.getType().equals("Nightlife")) ||
                    (preferences.outdoor && activity.getType().equals("Outdoor"))) {
                filteredActivities.add(activity);
            }
        }
        return filteredActivities;
    }

    private List<ActivityOption> createMockActivities() {
        List<ActivityOption> mockActivities = new ArrayList<>();
        mockActivities.add(new ActivityOption("Cafe Mocha", "cafe123", "123 Coffee Street", "http://cafemocha.com", 4.5f, "Restaurant"));
        mockActivities.add(new ActivityOption("Night Club", "club456", "456 Party Lane", "http://nightclub.com", 4.0f, "Nightlife"));
        // Add more mock items as needed
        return mockActivities;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_finalize_plan) {
            returnSelectedActivitiesToInitialPlanActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnSelectedActivitiesToInitialPlanActivity() {
        Set<ActivityOption> activitiesToReturn = adapter.getSelectedActivities();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("addedActivities", new ArrayList<>(activitiesToReturn));
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
