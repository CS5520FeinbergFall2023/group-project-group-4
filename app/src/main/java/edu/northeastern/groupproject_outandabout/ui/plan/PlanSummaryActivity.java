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

        // Retrieve the selected activities passed from ActivityListActivity
        List<ActivityOption> selectedActivities = getSelectedActivities();

        adapter = new PlanSummaryAdapter(selectedActivities);
        planSummaryRecyclerView.setAdapter(adapter);
    }

    private List<ActivityOption> getSelectedActivities() {
        // Retrieve the list of selected activities from the Intent
        List<ActivityOption> selectedActivities = (List<ActivityOption>) getIntent().getSerializableExtra("selectedActivities");
        return selectedActivities != null ? selectedActivities : new ArrayList<>();
    }
}
