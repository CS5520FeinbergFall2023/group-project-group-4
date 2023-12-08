package edu.northeastern.groupproject_outandabout.ui.plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;

public class CustomizeSearchActivity extends AppCompatActivity {

    private TextView testTV;
    private Spinner searchRadiusSpinner;
    private Spinner minRatingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_search);

        testTV = findViewById(R.id.typeTestTV);
        searchRadiusSpinner = findViewById(R.id.searchRadiusSpinner);
        minRatingSpinner = findViewById(R.id.minRatingSpinner);

        // Get intent to determine the customization type
        Intent intent = getIntent();
        setLayoutByType((ActivityType)intent.getSerializableExtra("ActivityType"));

        // Setup search radius and min rating spinners
        setUpSpinners();
    }

    //TODO: Make this method change the filtering options based on activity type
    private void setLayoutByType(ActivityType type) {
        Log.d("TEST", "setLayoutByType: " + type);
        switch (type) {
            case RESTAURANT:
                //restaurant layout
                testTV.setText("Restaurant");
                break;
            case ENTERTAINMENT:
                //entertainment layout
                testTV.setText("Entertainment");
                break;
            case NIGHTLIFE:
                //nightlife layout
                testTV.setText("Nightlife");
                break;
            case OUTDOORS:
                //outdoors layout
                testTV.setText("Outdoor");
                break;
            default:
                testTV.setText("None");

        }
    }

    private void setUpSpinners() {
        // Search Radius
        ArrayAdapter<CharSequence> searchRadiusAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.search_Radius_Options,
                android.R.layout.simple_spinner_item
        );
        searchRadiusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchRadiusSpinner.setAdapter(searchRadiusAdapter);

        // Minimum Rating
        ArrayAdapter<CharSequence> minRatingAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.min_Rating_Options,
                android.R.layout.simple_spinner_item
        );
        minRatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minRatingSpinner.setAdapter(minRatingAdapter);
    }
}