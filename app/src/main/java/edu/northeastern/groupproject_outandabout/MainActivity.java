package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.groupproject_outandabout.ui.plan.NewPlanActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BUTTON FOR PLAN TESTING
        Button btnNewPlan = findViewById(R.id.btnNewPlan);
        btnNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewPlanActivity.class);
                startActivity(intent);
            }
        });

        // BUTTON FOR API TEST ACTIVITY
        Button apiTest = findViewById(R.id.apiTestButton);
        apiTest.setOnClickListener(view -> {
            Intent intent = new Intent(this, ApiTestActivity.class);
            startActivity(intent);
        });
    }
}