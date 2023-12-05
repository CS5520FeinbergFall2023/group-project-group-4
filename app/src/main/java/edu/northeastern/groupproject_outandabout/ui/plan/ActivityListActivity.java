/**
 * IGNORE THIS FILE NOW USING RUPERT SWIPING
 *
 */




package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.groupproject_outandabout.ActivityType;
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

        // Replace this with actual filtering logic based on received parameters
        List<ActivityOption> filteredActivities = createMockActivities();
        adapter = new ActivityAdapter(filteredActivities);
        activitiesRecyclerView.setAdapter(adapter);
    }

    private List<ActivityOption> createMockActivities() {
        // Mock activities for testing. Replace with real data fetching logic.
        List<ActivityOption> mockActivities = new ArrayList<>();
        mockActivities.add(new ActivityOption("Cafe Mocha", "cafe123", "123 Coffee Street", "http://cafemocha.com", 4.5f, ActivityType.RESTAURANT));
        mockActivities.add(new ActivityOption("Night Club", "club456", "456 Party Lane", "http://nightclub.com", 4.0f, ActivityType.NIGHTLIFE));
        // Add more mock items as needed
        return mockActivities;
    }

    private void addSelectedActivitiesToPlan() {
        Set<ActivityOption> activitiesToAdd = adapter.getSelectedActivities();
        selectedActivities.addAll(activitiesToAdd);
        Toast.makeText(this, "Activities added to plan", Toast.LENGTH_SHORT).show();
    }

    private void setupButtons() {
        Button btnAddToPlan = findViewById(R.id.btnAddToPlan);
        btnAddToPlan.setOnClickListener(view -> addSelectedActivitiesToPlan());

        Button btnViewPlan = findViewById(R.id.btnViewPlan);
        btnViewPlan.setOnClickListener(view -> returnSelectedActivitiesToInitialPlanActivity());
    }

    private void returnSelectedActivitiesToInitialPlanActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("selectedActivities", new ArrayList<>(selectedActivities));
        setResult(RESULT_OK, returnIntent);
        finish();
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
}
