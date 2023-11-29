package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import edu.northeastern.groupproject_outandabout.R;

public class NewPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);

        findViewById(R.id.startPlanningButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ActivitySelectionActivity
                Intent intent = new Intent(NewPlanActivity.this, ActivitySelectionActivity.class);
                startActivity(intent);
            }
        });

    }
}
