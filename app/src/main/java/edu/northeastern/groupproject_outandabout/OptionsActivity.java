package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;

import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * This clas shows the user either the liked or disliked activities and allows
 * them to select one of them to include in their plan
 */
public class OptionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ActivityOption> activityOptions;
    private OptionAdapter adapter;
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        selectButton = findViewById(R.id.selectButton);
        selectButton.setEnabled(false);

        activityOptions = getIntent().getParcelableArrayListExtra("activityList");

        recyclerView = findViewById(R.id.optionsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OptionAdapter(activityOptions);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            for (int i = 0; i < activityOptions.size(); i++) {
                if (i != position) {
                    activityOptions.get(i).setSelected(false);
                    adapter.notifyItemChanged(i);
                }
            }

            ActivityOption clickedItem = activityOptions.get(position);
            clickedItem.setSelected(!clickedItem.isSelected());
            adapter.notifyItemChanged(position);

            boolean anyItemSelected = activityOptions.stream().anyMatch(ActivityOption::isSelected);
            selectButton.setEnabled(anyItemSelected);
        });

        selectButton.setOnClickListener(view -> {
            ActivityOption selectedOption = null;
            for (ActivityOption option : activityOptions) {
                if (option.isSelected()) {
                    selectedOption = option;
                    break;
                }
            }

            if (selectedOption != null) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("SelectedActivity", (Parcelable) selectedOption);
                setResult(RESULT_OK, returnIntent);
                finish();
            } else {
                // Handle the case where no activity is selected
            }
        });
    }
}