package edu.northeastern.groupproject_outandabout.ui.plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.SwipeActivity;
import edu.northeastern.groupproject_outandabout.api.GooglePlacesCaller;

public class PreSwipeActivity extends AppCompatActivity {

    private TextView currSearchActivityTV;
    private ProgressBar searchLoadingWheel;

    private Plan plan;
    private int searchActivityIndex = 0;
    private Handler threadHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_swipe);

        currSearchActivityTV = findViewById(R.id.currSearchActivityTV);
        searchLoadingWheel = findViewById(R.id.searchLoadingWheel);
        setUpButtons();

        // Get Plan object from initial plan
        Intent prevIntent = getIntent();
        plan = (Plan)prevIntent.getSerializableExtra("Plan");

        // Checking that Plan object is correctly passed with ActivityBuilderSlot list
        for(ActivityBuilderSlot slot : plan.getActivitySlots()) {
            Log.d("BUILDER_SLOTS", slot.getType().toString() + " --- " + slot.getTimeslot());
        }

        // Update current search activity text
        currSearchActivityTV.setText("Activity " + (searchActivityIndex+1) + ": "
                                    + plan.getActivitySlots().get(searchActivityIndex).getType().toString() + " @ "
                                    + plan.getActivitySlots().get(searchActivityIndex).getTimeslot());
    }

    private void setUpButtons() {
        setUpRandomButton();
        setUpCustomizeButton();
    }

    private void setUpRandomButton() {
        // Randomize Search Button
        Button randomizeButton = findViewById(R.id.randomizeButton);
        randomizeButton.setOnClickListener(view -> {
            // Make API call and pass the response as a String to SwipeActivity
            Thread apiThread = new Thread(new RandomSearchRunnable());
            apiThread.start();
        });
    }

    private void setUpCustomizeButton() {
        // Customize Search Button
        Button customizeButton = findViewById(R.id.customizeButton);
        customizeButton.setOnClickListener(view -> {
            //TODO: Implement Customize Search Activity/Features
        });
    }

    /**
     * Helper method to format the Google Places API search query for a random activity search
     */
    private String formatRandomGoogleApiQuery() {
        return "";
    }

    /**
     * Runnable class used for API random search thread
     */
    class RandomSearchRunnable implements Runnable {

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            threadHandler.post(() -> searchLoadingWheel.setVisibility(View.VISIBLE));

            String testParameters = "{\n" +
                    "  \"includedTypes\": [\"restaurant\"],\n" +
                    "  \"maxResultCount\": 3,\n" +
                    "  \"locationRestriction\": {\n" +
                    "    \"circle\": {\n" +
                    "      \"center\": {\n" +
                    "        \"latitude\": 37.7937,\n" +
                    "        \"longitude\": -122.3965},\n" +
                    "      \"radius\": 500.0\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            String response = GooglePlacesCaller.fetchPoiData(testParameters);

            // Show user loading wheel for at least 1 sec
            while (System.currentTimeMillis() - startTime < 1000) {/*wait*/}
            threadHandler.post(() -> searchLoadingWheel.setVisibility(View.INVISIBLE));

            Intent intent = new Intent(PreSwipeActivity.this, SwipeActivity.class);
            intent.putExtra("Response", response);
            threadHandler.post(() -> startActivity(intent));
        }
    }
}