package edu.northeastern.groupproject_outandabout.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.groupproject_outandabout.R;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // Firebase Auth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        final EditText editTextEmail = findViewById(R.id.editTextEmail);
        final EditText editTextPassword = findViewById(R.id.editTextPassword);
        final EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            // Check if the fields are filled and if passwords match
            if (!email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
                // Proceed with registration
                registerUser(email, password);
            } else {
                // Handle error
                Toast.makeText(RegisterActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration success
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        // Redirect to login activity
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        // Registration error
                        Log.e("RegisterActivity", "Registration failed", task.getException());
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
