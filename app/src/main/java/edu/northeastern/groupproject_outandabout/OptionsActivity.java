package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * This clas shows the user either the liked or disliked activities and allows
 * them to select one of them to include in their plan
 */
public class OptionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView title;
    private ImageView image;
    private List<ActivityOption> activityOptions;
    private OptionAdapter adapter;
    private Button selectButton;
    private boolean likeFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        title = findViewById(R.id.title);
        image = findViewById(R.id.imageView);
        selectButton = findViewById(R.id.selectButton);
        selectButton.setEnabled(false);

        activityOptions = getIntent().getParcelableArrayListExtra("activityList");
        likeFlag = getIntent().getBooleanExtra("likeFlag", false);

        if (likeFlag) {
            title.setText("Liked Activities");
            Drawable drawable = ContextCompat.getDrawable(OptionsActivity.this, R.drawable.liked_activities);
            image.setImageDrawable(drawable);
        } else {
            title.setText("Disliked Activities");
            Drawable drawable = ContextCompat.getDrawable(OptionsActivity.this, R.drawable.disliked_activities);
            image.setImageDrawable(drawable);
        }

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
                returnIntent.putExtra("SelectedActivity", (Serializable)selectedOption);
                setResult(RESULT_OK, returnIntent);
                finish();
            } else {
                // Handle the case where no activity is selected
            }
        });
    }
}