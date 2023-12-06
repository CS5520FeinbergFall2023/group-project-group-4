package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.northeastern.groupproject_outandabout.ui.login.LoginActivity;
import edu.northeastern.groupproject_outandabout.ui.plan.NewPlanActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Plan Buttons
        Button swipeTest = findViewById(R.id.swipeTestButton);

        // Welcome message for logged in user
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        // auth buttons
        Button logoutButton = findViewById(R.id.logoutButton);
        Button loginButton = findViewById(R.id.loginButton);

        //user account buttons
        Button userAccountButton = findViewById(R.id.userAccountButton);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // singed in user display their name
            String welcomeText = "Welcome, " + currentUser.getEmail();
            welcomeMessage.setText(welcomeText);
            welcomeMessage.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
            userAccountButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);



        } else {
            // No user is signed in, do not show fields
            welcomeMessage.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
            userAccountButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);

        }

        // BUTTON FOR PLAN TESTING
        Button btnNewPlan = findViewById(R.id.btnNewPlan);
        btnNewPlan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewPlanActivity.class);
            startActivity(intent);
        });

        // BUTTON FOR API TEST ACTIVITY
        Button apiTest = findViewById(R.id.apiTestButton);
        apiTest.setOnClickListener(view -> {
            Intent intent = new Intent(this, ApiTestActivity.class);
            startActivity(intent);
        });

        // BUTTON FOR SWIPE TEST ACTIVITY
        swipeTest.setOnClickListener(view -> {
            Intent intent = new Intent(this, SwipeActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            // Sign out from Firebase
            FirebaseAuth.getInstance().signOut();

            // Redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        loginButton.setOnClickListener(v -> {
            // Redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        userAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserAccountActivity.class);
            startActivity(intent);
        });
    }
}