package edu.northeastern.groupproject_outandabout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;
import edu.northeastern.groupproject_outandabout.util.FirebaseDatabaseUtil;
import edu.northeastern.groupproject_outandabout.util.FirebaseDatabaseUtil.SimplePlan;
import edu.northeastern.groupproject_outandabout.util.PlanAdapter;

public class UserAccountActivity extends AppCompatActivity {

    private TextView userEmailTextView;
    private TextView welcomeTextView;
    private RecyclerView plansRecyclerView;
    private PlanAdapter planAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        userEmailTextView = findViewById(R.id.userEmailTextView);
        welcomeTextView = findViewById(R.id.welcomeTextView);
        plansRecyclerView = findViewById(R.id.plansRecyclerView);

        plansRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        planAdapter = new PlanAdapter(new ArrayList<>());
        plansRecyclerView.setAdapter(planAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userEmailTextView.setText(currentUser.getEmail());
            userEmailTextView.setVisibility(View.VISIBLE);
            welcomeTextView.setText("Welcome " + currentUser.getDisplayName() + "!");
            fetchUserPlans();
        } else {
            userEmailTextView.setText("No user is signed in.");
            welcomeTextView.setVisibility(View.GONE);
        }
    }

    private void fetchUserPlans() {
        FirebaseDatabaseUtil dbUtil = new FirebaseDatabaseUtil();
        dbUtil.fetchPlansForUser(new FirebaseDatabaseUtil.DatabaseReadCallback() {
            @Override
            public void onSuccess(List<SimplePlan> plans) {
                runOnUiThread(() -> planAdapter.updatePlans(plans));
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    // Handle failure case, maybe show a toast or a placeholder text
                });
            }
        });
    }
}
