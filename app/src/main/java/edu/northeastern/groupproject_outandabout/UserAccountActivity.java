package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAccountActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        TextView userEmailTextView = findViewById(R.id.userEmailTextView);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // user signed in
            userEmailTextView.setText(currentUser.getEmail());
            userEmailTextView.setVisibility(View.VISIBLE);
        } else {
            // no user signed in
            userEmailTextView.setText("No user is signed in.");
        }
    }
}
