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

        // Mock data for testing, replace with actual data later
        List<ActivityOption> mockActivities = createMockActivities();
        adapter = new ActivityAdapter(mockActivities);
        activitiesRecyclerView.setAdapter(adapter);

        // Add to Plan button
        Button btnAddToPlan = findViewById(R.id.btnAddToPlan);
        btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedActivities.addAll(adapter.getSelectedActivities());
            }
        });

        // View Current Plan button
        Button btnViewPlan = findViewById(R.id.btnViewPlan);
        btnViewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityListActivity.this, PlanSummaryActivity.class);
                // Pass the selected activities to PlanSummaryActivity
                intent.putExtra("selectedActivities", new ArrayList<>(selectedActivities));
                startActivity(intent);
            }
        });
    }

    private List<ActivityOption> createMockActivities() {
        List<ActivityOption> mockActivities = new ArrayList<>();
        mockActivities.add(new ActivityOption("Cafe Mocha", "cafe123", "123 Coffee Street", "http://cafemocha.com", 4.5f));
        mockActivities.add(new ActivityOption("Night Club", "club456", "456 Party Lane", "http://nightclub.com", 4.0f));
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
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_finalize_plan) {
            // Retrieve selected activities
            Set<ActivityOption> selectedActivities = adapter.getSelectedActivities();
            Intent intent = new Intent(this, PlanSummaryActivity.class);
            // Pass selectedActivities to the next activity (may require serialization or a different approach)
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
