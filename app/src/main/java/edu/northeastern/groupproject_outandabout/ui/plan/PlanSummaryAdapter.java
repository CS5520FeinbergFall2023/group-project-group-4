package edu.northeastern.groupproject_outandabout.ui.plan;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.northeastern.groupproject_outandabout.R;


public class PlanSummaryAdapter extends RecyclerView.Adapter<PlanSummaryAdapter.PlanViewHolder> {

    private List<ActivityOption> activities;

    public PlanSummaryAdapter(List<ActivityOption> activities) {
        this.activities = activities;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_summary_item, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        ActivityOption activity = activities.get(position);
        holder.activityNameTextView.setText(activity.getName());

        // Set up a listener for the EditText
        holder.activityTimeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Here, you can handle the time input, validate it, or store it
                // For example, you might want to update a map with activity IDs and their corresponding times
            }
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    static class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView activityNameTextView;
        EditText activityTimeEditText;

        PlanViewHolder(View itemView) {
            super(itemView);
            activityNameTextView = itemView.findViewById(R.id.activityNameTextView);
            activityTimeEditText = itemView.findViewById(R.id.activityTimeEditText);
        }
    }
}
