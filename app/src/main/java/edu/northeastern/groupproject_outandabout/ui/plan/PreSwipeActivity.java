package edu.northeastern.groupproject_outandabout.ui.plan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.SwipeActivity;
import edu.northeastern.groupproject_outandabout.api.GooglePlacesCaller;

public class PreSwipeActivity extends AppCompatActivity {

    private TextView currSearchActivityTV;
    private ProgressBar searchLoadingWheel;

    private Plan plan;
    private int searchActivityIndex = 0;
    private final Handler threadHandler = new Handler();

    private static final int REQUEST_CODE_SWIPE_ACTIVITY = 200;
    private static final int REQUEST_CODE_CUSTOMIZE_ACTIVITY = 300;

    private static final float DEFAULT_SEARCH_RADIUS_MI = 2.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_swipe);

        this.currSearchActivityTV = findViewById(R.id.currSearchActivityTV);
        this.searchLoadingWheel = findViewById(R.id.searchLoadingWheel);
        setUpButtons();

        // Get Plan object from initial plan
        Intent prevIntent = getIntent();
        this.plan = (Plan)prevIntent.getSerializableExtra("Plan");

        // Update current search activity text
        updateCurrSearchText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If customized search query returned from Customize Activity, call api and launch swiping activity
        if (requestCode == REQUEST_CODE_CUSTOMIZE_ACTIVITY && resultCode == RESULT_OK
                && data.hasExtra("CustomQuery")) {
            Thread apiThread = new Thread(new ApiSearchRunnable(data.getStringExtra("CustomQuery")));
            apiThread.start();
        }
        // If ActivityOption was selected and returned from swiping, add to plan
        else if (requestCode == REQUEST_CODE_SWIPE_ACTIVITY && resultCode == RESULT_OK
                && data.hasExtra("SelectedActivity")) {
            ActivityOption selectedActivity = (ActivityOption)data.getSerializableExtra("SelectedActivity");

            this.plan.addSelectedActivity(selectedActivity);
            this.searchActivityIndex++;

            // Check if last activity was selected and if so move on to plan summary
            if(searchActivityIndex >= this.plan.getActivitySlots().size()) {
                Intent intent = new Intent(this, PlanSummaryActivity.class);
                intent.putExtra("Plan", this.plan);
                startActivity(intent);
            }
            // If more activity slots to select for, update textview
            else {
                updateCurrSearchText();
            }
        }
    }

    /**
     * Exit confirmation added to back press
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Exit Plan Building? Selected Activities will be lost.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Updates the textview to display the activity slot the user is currently searching for
     */
    private void updateCurrSearchText() {
        this.currSearchActivityTV.setText("Activity " + (searchActivityIndex+1) + ": "
                + this.plan.getActivitySlots().get(searchActivityIndex).getType().toString() + " @ "
                + this.plan.getActivitySlots().get(searchActivityIndex).getTimeslot());
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
            String searchQuery = formatRandomGoogleApiQuery();
            Thread apiThread = new Thread(new ApiSearchRunnable(searchQuery));
            apiThread.start();
        });
    }

    private void setUpCustomizeButton() {
        // Customize Search Button
        Button customizeButton = findViewById(R.id.customizeButton);
        customizeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CustomizeSearchActivity.class);
            intent.putExtra("ActivityType", this.plan.getActivitySlots().get(searchActivityIndex).getType());
            startActivityForResult(intent, REQUEST_CODE_CUSTOMIZE_ACTIVITY);
        });
    }

    /**
     * Helper method to format the Google Places API search query for a random activity search
     * //TODO: Implement logic to get correct search location and activity type
     */
    private String formatRandomGoogleApiQuery() {
        // Determine "includedTypes" to add to query based on what type of activity is being searched
        ActivityType searchType = this.plan.getActivitySlots().get(searchActivityIndex).getType();
        String includedTypes = "";
        switch (searchType) {
            case RESTAURANT:
                includedTypes = "[\"restaurant\"]";
                break;
            case ENTERTAINMENT:
                break;
            case NIGHTLIFE:
                break;
            case OUTDOORS:
                break;
            default:
        }

        // Get plan latitude and longitude
        String searchLat = Double.toString(this.plan.getLatitude());
        String searchLong = Double.toString(this.plan.getLongitude());

        // Set default search radius
        String searchRadius = Float.toString(milesToMeters(DEFAULT_SEARCH_RADIUS_MI));

        return "{\n" +
                "  \"includedTypes\": " + includedTypes + ",\n" +
                "  \"maxResultCount\": 3,\n" +
                "  \"locationRestriction\": {\n" +
                "    \"circle\": {\n" +
                "      \"center\": {\n" +
                "        \"latitude\": " + searchLat + ",\n" +
                "        \"longitude\": " + searchLong + "},\n" +
                "      \"radius\": " + searchRadius + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    /**
     *  Helper function to convert miles into meters used to set search query radius
     */
    private float milesToMeters(float miles) { return miles * 1609.34f; }

    /**
     * Runnable class used for to run an API search in a thread
     */
    class ApiSearchRunnable implements Runnable {
        private final String searchQuery;

        public ApiSearchRunnable(String searchQuery) {
            this.searchQuery = searchQuery;
        }
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            threadHandler.post(() -> searchLoadingWheel.setVisibility(View.VISIBLE));

            // MOCK RESPONSE USED FOR NOW TO TEST PARSING LOGIC
            //String response = GooglePlacesCaller.fetchPoiData(this.searchQuery);
            String response = GooglePlacesCaller.getMockApiResponse();

            // Show user loading wheel for at least 1 sec
            while (System.currentTimeMillis() - startTime < 1000) {/*wait*/}
            threadHandler.post(() -> searchLoadingWheel.setVisibility(View.INVISIBLE));

            Intent intent = new Intent(PreSwipeActivity.this, SwipeActivity.class);
            intent.putExtra("Response", response);
            threadHandler.post(() -> startActivityForResult(intent, REQUEST_CODE_SWIPE_ACTIVITY));
        }
    }
}