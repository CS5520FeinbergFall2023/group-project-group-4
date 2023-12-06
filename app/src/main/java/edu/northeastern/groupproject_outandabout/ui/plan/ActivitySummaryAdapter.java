package edu.northeastern.groupproject_outandabout.ui.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.groupproject_outandabout.R;

public class ActivitySummaryAdapter extends RecyclerView.Adapter<ActivitySummaryAdapter.ActivitySummaryViewHolder> {

    private List<ActivityOption> activityOptions;

    public ActivitySummaryAdapter(List<ActivityOption> activityOptions) {
        this.activityOptions = activityOptions;
    }

    @Override
    public ActivitySummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_summary_item, parent, false);
        return new ActivitySummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivitySummaryViewHolder holder, int position) {
        ActivityOption activityOption = activityOptions.get(position);
        holder.bind(activityOption);
    }

    @Override
    public int getItemCount() {
        return activityOptions.size();
    }

    static class ActivitySummaryViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView addressTextView;
        private TextView ratingTextView;
        private TextView typeTextView;
        private TextView timeTextView;

        public ActivitySummaryViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }

        public void bind(ActivityOption activityOption) {
            nameTextView.setText(activityOption.getName());
            addressTextView.setText(activityOption.getAddress());
            ratingTextView.setText("Rating: " + activityOption.getRating());
            typeTextView.setText("Type: " + activityOption.getSelectedType().toString());
            timeTextView.setText("Time: " + activityOption.getSelectedTime());
        }
    }
}
