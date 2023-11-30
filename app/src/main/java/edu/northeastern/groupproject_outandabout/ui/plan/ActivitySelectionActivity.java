package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.groupproject_outandabout.R;
import java.io.Serializable;

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
        // Create a user preferences object with the selected options
        UserPreferences preferences = new UserPreferences(
                checkboxRestaurant.isChecked(),
                checkboxEntertainment.isChecked(),
                checkboxNightlife.isChecked(),
                checkboxOutdoor.isChecked()
        );

        // Navigate to the next activity with the user's preferences
        navigateToActivityList(preferences);
    }

    private void navigateToActivityList(UserPreferences preferences) {
        Intent activityListIntent = new Intent(ActivitySelectionActivity.this, ActivityListActivity.class);
        activityListIntent.putExtra("UserPreferences", preferences);
        startActivity(activityListIntent);
    }

    // Inner class for user preferences, implementing Serializable
    static class UserPreferences implements Serializable {
        boolean restaurant;
        boolean entertainment;
        boolean nightlife;
        boolean outdoor;

        public UserPreferences(boolean restaurant, boolean entertainment, boolean nightlife, boolean outdoor) {
            this.restaurant = restaurant;
            this.entertainment = entertainment;
            this.nightlife = nightlife;
            this.outdoor = outdoor;
        }

        // Getters for the properties, if needed
    }
}
