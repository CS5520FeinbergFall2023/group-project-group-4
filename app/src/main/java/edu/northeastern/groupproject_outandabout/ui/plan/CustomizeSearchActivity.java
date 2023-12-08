package edu.northeastern.groupproject_outandabout.ui.plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;

public class CustomizeSearchActivity extends AppCompatActivity {

    private TextView testTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_search);

        testTV = findViewById(R.id.typeTestTV);

        // Get intent to determine the customization type
        Intent intent = getIntent();
        setLayoutByType((ActivityType)intent.getSerializableExtra("ActivityType"));

    }

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
}