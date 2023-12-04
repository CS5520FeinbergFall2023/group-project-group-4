package edu.northeastern.groupproject_outandabout.ui.plan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.R;

public class InitialPlanActivity extends AppCompatActivity {

    private RecyclerView initialPlanRecyclerView;
    private PlanSummaryAdapter adapter;
    private List<ActivityOption> selectedActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_plan);

        if (savedInstanceState != null) {
            // Restore the previously selected activities
            selectedActivities = (List<ActivityOption>) savedInstanceState.getSerializable("selectedActivities");
        } else {
            selectedActivities = new ArrayList<>();
        }

        initialPlanRecyclerView = findViewById(R.id.initialPlanRecyclerView);
        initialPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlanSummaryAdapter(selectedActivities);
        initialPlanRecyclerView.setAdapter(adapter);

        findViewById(R.id.addActivitiesButton).setOnClickListener(v -> {
            Intent intent = new Intent(InitialPlanActivity.this, ActivitySelectionActivity.class);
            startActivity(intent);
        });

        setupFinalizePlanButton();
    }

    private void setupFinalizePlanButton() {
        Button finalizePlanButton = findViewById(R.id.finalizePlanButton);
        finalizePlanButton.setOnClickListener(v -> {
            new AlertDialog.Builder(InitialPlanActivity.this)
                    .setTitle("Confirm Finalization")
                    .setMessage("Are you sure you want to finalize your plan?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(InitialPlanActivity.this, PlanSummaryActivity.class);
                        intent.putExtra("selectedActivities", new ArrayList<>(selectedActivities));
                        startActivity(intent);
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selectedActivities", new ArrayList<>(selectedActivities));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("selectedActivities")) {
            List<ActivityOption> newActivities = (List<ActivityOption>) intent.getSerializableExtra("selectedActivities");
            if (newActivities != null) {
                selectedActivities.addAll(newActivities);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
