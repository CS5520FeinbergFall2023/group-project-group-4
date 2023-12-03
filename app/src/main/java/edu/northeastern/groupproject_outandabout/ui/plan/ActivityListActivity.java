package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

        activitiesRecyclerView = findViewById(R.id.activitiesRecyclerView);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve user preferences and filter activities accordingly
        ActivitySelectionActivity.UserPreferences preferences = (ActivitySelectionActivity.UserPreferences) getIntent().getSerializableExtra("UserPreferences");
        List<ActivityOption> filteredActivities = filterActivitiesBasedOnPreferences(preferences, createMockActivities());
        adapter = new ActivityAdapter(filteredActivities);
        activitiesRecyclerView.setAdapter(adapter);


        // "Add to Plan" button
        Button btnAddToPlan = findViewById(R.id.btnAddToPlan);
        btnAddToPlan.setOnClickListener(view -> {
            // Get selected activities
            Set<ActivityOption> activitiesToAdd = adapter.getSelectedActivities();

            // Add selected activities to the InitialPlanActivity
            InitialPlanActivity.addActivitiesToInitialPlan(activitiesToAdd);

            // Finish the current activity
            finish();
        });

        // "View Current Plan" button
        Button btnViewPlan = findViewById(R.id.btnViewPlan);
        btnViewPlan.setOnClickListener(view -> {
            // View the current plan
            Intent viewPlanIntent = new Intent(ActivityListActivity.this, InitialPlanActivity.class);
            startActivity(viewPlanIntent);
        });

    }

    private List<ActivityOption> filterActivitiesBasedOnPreferences(ActivitySelectionActivity.UserPreferences preferences, List<ActivityOption> allActivities) {
        if (preferences == null) return allActivities; // If no preferences are passed, return all activities
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_finalize_plan) {
            returnSelectedActivitiesToInitialPlanActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnSelectedActivitiesToInitialPlanActivity() {
        // Get selected activities
        Set<ActivityOption> activitiesToReturn = adapter.getSelectedActivities();

        // Prepare data to send back to InitialPlanActivity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("addedActivities", new ArrayList<>(activitiesToReturn));
        setResult(RESULT_OK, returnIntent);

        // Finish ActivityListActivity and return to InitialPlanActivity
        finish();
    }



}
