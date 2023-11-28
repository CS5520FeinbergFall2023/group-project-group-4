package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.groupproject_outandabout.R;

public class ActivitySelectionActivity extends AppCompatActivity {

    private CheckBox checkboxRestaurant, checkboxEntertainment, checkboxNightlife, checkboxOutdoor;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // Initialize checkboxes
        checkboxRestaurant = findViewById(R.id.checkbox_restaurant);
        checkboxEntertainment = findViewById(R.id.checkbox_entertainment);
        checkboxNightlife = findViewById(R.id.checkbox_nightlife);
        checkboxOutdoor = findViewById(R.id.checkbox_outdoor);

        // Initialize confirm button
        confirmButton = findViewById(R.id.confirmButton);

        // Set up confirm button click listener
        confirmButton.setOnClickListener(view -> onConfirmSelection());
    }

    private void onConfirmSelection() {
        // Collect selected options
        boolean isRestaurantSelected = checkboxRestaurant.isChecked();
        boolean isEntertainmentSelected = checkboxEntertainment.isChecked();
        boolean isNightlifeSelected = checkboxNightlife.isChecked();
        boolean isOutdoorSelected = checkboxOutdoor.isChecked();

        // TODO: Use these selections to fetch data from the Places API

        // For now, just navigate to the next activity (e.g., a summary or list plan activity)
        // Replace 'NextActivity.class' with the actual class of your next activity
        Intent nextActivityIntent = new Intent(ActivitySelectionActivity.this, NextActivity.class);
        nextActivityIntent.putExtra("RestaurantSelected", isRestaurantSelected);
        nextActivityIntent.putExtra("EntertainmentSelected", isEntertainmentSelected);
        nextActivityIntent.putExtra("NightlifeSelected", isNightlifeSelected);
        nextActivityIntent.putExtra("OutdoorSelected", isOutdoorSelected);
        startActivity(nextActivityIntent);
    }
}
