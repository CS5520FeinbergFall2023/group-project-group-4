package edu.northeastern.groupproject_outandabout.util;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.groupproject_outandabout.R;

public class PlanViewHolder extends RecyclerView.ViewHolder {
    TextView planNameTextView, activitiesTextView;
    // Other views

    public PlanViewHolder(View itemView) {
        super(itemView);
        planNameTextView = itemView.findViewById(R.id.planNameTextView);
        activitiesTextView = itemView.findViewById(R.id.activitiesTextView);
        // Initialize other views if any
    }

    public void setOnAddressClickListener(View.OnClickListener listener) {
        activitiesTextView.setOnClickListener(listener);
    }
}
