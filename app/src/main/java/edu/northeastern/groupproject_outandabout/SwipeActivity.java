package edu.northeastern.groupproject_outandabout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * This is the activity where users swipe through the given activity options
 */
public class SwipeActivity extends AppCompatActivity {
    private List<ActivityOption> activities;
    private List<ActivityOption> savedActivities;
    private List<ActivityOption> removedActivities;
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private Button savedButton;
    private Button removedButton;
    private int lastSwipedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        savedActivities = new ArrayList<>();
        removedActivities = new ArrayList<>();

        savedButton = findViewById(R.id.savedButton);
        removedButton = findViewById(R.id.removedButton);
        updateButtonState();

        savedButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, OptionsActivity.class);
            intent.putParcelableArrayListExtra("activityList", new ArrayList<>(savedActivities));
            startActivity(intent);
        });

        removedButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, OptionsActivity.class);
            intent.putParcelableArrayListExtra("activityList", new ArrayList<>(removedActivities));
            startActivity(intent);
        });

        // Initialize the list of dummy ActivityOption objects
        // To be replaced later with the results of the API call
        activities = generateDummyData();

        recyclerView = findViewById(R.id.activityCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardAdapter(activities);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position < activities.size()) {
                    ActivityOption swipedItem = activities.remove(position);

                    if (direction == ItemTouchHelper.LEFT) {
                        removedActivities.add(swipedItem);
                    } else if (direction == ItemTouchHelper.RIGHT) {
                        savedActivities.add(swipedItem);
                    }

                    activities.add(0, swipedItem);
                    adapter.notifyItemMoved(position, 0);
                }

                updateButtonState();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void updateButtonState() {
        savedButton.setEnabled(!savedActivities.isEmpty());
        removedButton.setEnabled(!removedActivities.isEmpty());
    }

    private List<ActivityOption> generateDummyData() {
        List<ActivityOption> dummyData = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            String name = "Activity " + (i + 1);
            String id = "ID " + (i + 1);
            String address = "Address " + (i + 1);
            String websiteUri = "https://example.com/" + (i + 1);
            float rating = 4.0f;

            // ADDED EMPTY STRING FOR TYPE FIELD TO TEST IF APP WOULD BUILD
            ActivityOption activityOption = new ActivityOption(name, id, address, websiteUri, rating, ActivityType.NONE);
            dummyData.add(activityOption);
        }

        return dummyData;
    }
}