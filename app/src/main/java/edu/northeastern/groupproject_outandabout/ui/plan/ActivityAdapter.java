package edu.northeastern.groupproject_outandabout.ui.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.northeastern.groupproject_outandabout.R;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<ActivityOption> activities;
    private Set<ActivityOption> selectedActivities = new HashSet<>();

    public ActivityAdapter(List<ActivityOption> activities) {
        this.activities = activities;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityOption activityOption = activities.get(position);
        holder.nameTextView.setText(activityOption.getName());
        holder.addressTextView.setText(activityOption.getAddress());
        holder.ratingTextView.setText("Rating: " + activityOption.getRating() + " / 5");

        // Set the checked state of the checkbox
        holder.selectCheckBox.setChecked(selectedActivities.contains(activityOption));
        holder.selectCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedActivities.add(activityOption);
            } else {
                selectedActivities.remove(activityOption);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView ratingTextView;
        CheckBox selectCheckBox; // Added CheckBox

        ActivityViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.activityName);
            addressTextView = itemView.findViewById(R.id.activityAddress);
            ratingTextView = itemView.findViewById(R.id.activityRating);
            selectCheckBox = itemView.findViewById(R.id.selectCheckBox); // Initialize CheckBox
        }
    }

    public Set<ActivityOption> getSelectedActivities() {
        return new HashSet<>(selectedActivities); // Return a copy to avoid external modification
    }
}
