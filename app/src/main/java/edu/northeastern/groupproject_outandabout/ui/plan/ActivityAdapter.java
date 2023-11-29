package edu.northeastern.groupproject_outandabout.ui.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.ActivityOption; // Updated import

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<ActivityOption> activities; // Changed to ActivityOption

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
        // Adjust or remove the typeTextView if it's not relevant
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView ratingTextView; // Keep or adjust as needed

        ActivityViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.activityName);
            addressTextView = itemView.findViewById(R.id.activityAddress);
            ratingTextView = itemView.findViewById(R.id.activityRating); // Initialize rating view
            // Initialize other views as needed
        }
    }
}
