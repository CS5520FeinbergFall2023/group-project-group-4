package edu.northeastern.groupproject_outandabout.ui.plan;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import edu.northeastern.groupproject_outandabout.R;
import java.util.ArrayList;
import java.util.List;

public class PlanSummaryActivity extends AppCompatActivity {

    private RecyclerView planSummaryRecyclerView;
    private PlanSummaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_summary);

        planSummaryRecyclerView = findViewById(R.id.planSummaryRecyclerView);
        planSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Retrieve the selected activities
        List<ActivityOption> selectedActivities = getSelectedActivities(); // Implement this method

        adapter = new PlanSummaryAdapter(selectedActivities);
        planSummaryRecyclerView.setAdapter(adapter);
    }

    private List<ActivityOption> getSelectedActivities() {
        // Implement logic to retrieve selected activities
        // Placeholder for demonstration
        return new ArrayList<>(); // Replace with actual implementation
    }
}
