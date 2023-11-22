package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BUTTON FOR API TEST ACTIVITY
        Button apiTest = findViewById(R.id.apiTestButton);
        apiTest.setOnClickListener(view -> {
            Intent intent = new Intent(this, ApiTestActivity.class);
            startActivity(intent);
        });
    }
}