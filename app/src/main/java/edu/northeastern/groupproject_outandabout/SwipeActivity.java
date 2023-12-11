package edu.northeastern.groupproject_outandabout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.api.GooglePlacesCaller;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * This is the activity where users swipe through the given activity options
 */
public class SwipeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_OPTIONS_ACTIVITY = 100;
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
            startOptionsActivityForResult(savedActivities, true);
        });

        removedButton.setOnClickListener(view -> {
            startOptionsActivityForResult(removedActivities, false);
        });

        activities = parseApiResponse(); // generateDummyData(); //

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

    private void startOptionsActivityForResult(List<ActivityOption> activities, boolean likeFlag) {
        Intent intent = new Intent(this, OptionsActivity.class);
        intent.putParcelableArrayListExtra("activityList", new ArrayList<>(activities));
        intent.putExtra("likeFlag", likeFlag);
        startActivityForResult(intent, REQUEST_CODE_OPTIONS_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If ActivityOption was selected in OptionActivity and returned, return it to PreSwipe Activity
        if (requestCode == REQUEST_CODE_OPTIONS_ACTIVITY && resultCode == RESULT_OK
            && data.hasExtra("SelectedActivity")) {
            Intent returnIntent = new Intent();
            ActivityOption selectedActivity = (ActivityOption)data.getSerializableExtra("SelectedActivity");
            returnIntent.putExtra("SelectedActivity", (Serializable)selectedActivity);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    private void updateButtonState() {
        savedButton.setEnabled(!savedActivities.isEmpty());
        removedButton.setEnabled(!removedActivities.isEmpty());
    }

    private List<ActivityOption> parseApiResponse() {
        Intent intent = getIntent();
        String apiResponse = intent.getStringExtra("Response");

        return GooglePlacesCaller.parseApiResponse(apiResponse, ActivityType.RESTAURANT);
    }

    private List<ActivityOption> generateDummyData() {
        List<ActivityOption> dummyData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = "Activity " + (i + 1);
            String address = "Address " + (i + 1);
            String rating = "4.0";
            ActivityOption activityOption = new ActivityOption(name, address, rating, ActivityType.NIGHTLIFE);
            dummyData.add(activityOption);
        }
        return dummyData;
    }
}