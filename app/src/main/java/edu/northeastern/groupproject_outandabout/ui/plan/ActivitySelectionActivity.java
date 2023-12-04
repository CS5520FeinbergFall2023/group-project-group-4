package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.Toast;
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

        initializeComponents();
        setupConfirmButton();
    }

    private void initializeComponents() {
        checkboxRestaurant = findViewById(R.id.checkbox_restaurant);
        checkboxEntertainment = findViewById(R.id.checkbox_entertainment);
        checkboxNightlife = findViewById(R.id.checkbox_nightlife);
        checkboxOutdoor = findViewById(R.id.checkbox_outdoor);
        confirmButton = findViewById(R.id.confirmButton);
    }

    private void setupConfirmButton() {
        confirmButton.setOnClickListener(view -> onConfirmSelection());
    }

    private void onConfirmSelection() {
        if (!isAnyCheckboxChecked()) {
            Toast.makeText(this, "Please select at least one category", Toast.LENGTH_SHORT).show();
            return;
        }

        UserPreferences preferences = createUserPreferences();
        navigateToActivityList(preferences);
    }

    private UserPreferences createUserPreferences() {
        return new UserPreferences(
                checkboxRestaurant.isChecked(),
                checkboxEntertainment.isChecked(),
                checkboxNightlife.isChecked(),
                checkboxOutdoor.isChecked()
        );
    }

    private boolean isAnyCheckboxChecked() {
        return checkboxRestaurant.isChecked() || checkboxEntertainment.isChecked() ||
                checkboxNightlife.isChecked() || checkboxOutdoor.isChecked();
    }

    private void navigateToActivityList(UserPreferences preferences) {
        Intent activityListIntent = new Intent(this, ActivityListActivity.class);
        activityListIntent.putExtra("UserPreferences", preferences);
        startActivity(activityListIntent);
    }

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
