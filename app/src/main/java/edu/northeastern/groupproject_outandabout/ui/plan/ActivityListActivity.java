package edu.northeastern.groupproject_outandabout.ui.plan;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.ActivityOption;

import java.util.ArrayList;
import java.util.List;

public class ActivityListActivity extends AppCompatActivity {

    private RecyclerView activitiesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        activitiesRecyclerView = findViewById(R.id.activitiesRecyclerView);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve user preferences passed from the previous activity
        ActivitySelectionActivity.UserPreferences preferences =
                (ActivitySelectionActivity.UserPreferences) getIntent().getSerializableExtra("UserPreferences");

        // Mock data for testing, replace with actual data later
        List<ActivityOption> mockActivities = createMockActivities();

        // Set up the RecyclerView with the mock data
        ActivityAdapter adapter = new ActivityAdapter(mockActivities);
        activitiesRecyclerView.setAdapter(adapter);
    }

    private List<ActivityOption> createMockActivities() {
        List<ActivityOption> mockActivities = new ArrayList<>();
        mockActivities.add(new ActivityOption("Cafe Mocha", "cafe123", "123 Coffee Street", "http://cafemocha.com", 4.5f));
        mockActivities.add(new ActivityOption("Night Club", "club456", "456 Party Lane", "http://nightclub.com", 4.0f));
        // Add more mock items as needed
        return mockActivities;
    }
}
