package edu.northeastern.groupproject_outandabout.ui.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;

public class PlanSummaryAdapter extends RecyclerView.Adapter<PlanSummaryAdapter.PlanViewHolder> {

    private List<ActivityOption> activities;

    public PlanSummaryAdapter(List<ActivityOption> activities) {
        this.activities = activities;
    }

    // New method to add activities
    public void addActivity(ActivityOption newActivity) {
        activities.add(newActivity);
        notifyDataSetChanged();  // Notify the adapter that the data set has changed
    }

    public ActivityType getSelectedType() {
        for (ActivityOption activity : activities) {
            if (!activity.isPlaceholder() && activity.isSelected()) {
                return activity.getSelectedType();
            }
        }
        return ActivityType.NONE;
    }

    public String getSelectedTime() {
        for (ActivityOption activity : activities) {
            if (!activity.isPlaceholder() && activity.isSelected()) {
                return activity.getSelectedTime();
            }
        }
        return "";
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        ActivityOption activity = activities.get(position);

        // Check if the activity is a placeholder or a selected activity
        if (activity.isPlaceholder()) {
            holder.activityLabel.setText("Select Activity " + (position + 1));
            setupSpinners(holder);
        } else {
            holder.activityLabel.setText(activity.getName());
            holder.timeSpinner.setVisibility(View.GONE);
            holder.typeSpinner.setVisibility(View.GONE);
        }
    }

    private void setupSpinners(PlanViewHolder holder) {
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.time_options, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.timeSpinner.setAdapter(timeAdapter);
        holder.timeSpinner.setVisibility(View.VISIBLE);

        // Setting up the type spinner with ActivityType enum
        ArrayAdapter<ActivityType> typeAdapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, ActivityType.values());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.typeSpinner.setAdapter(typeAdapter);
        holder.typeSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public List<ActivityOption> getSelectedActivities() {
        List<ActivityOption> selectedActivities = new ArrayList<>();
        for (ActivityOption activity : activities) {
            if (!activity.isPlaceholder()) {
                selectedActivities.add(activity);
            }
        }
        return selectedActivities;
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView activityLabel;
        Spinner timeSpinner;
        Spinner typeSpinner;

        PlanViewHolder(View itemView) {
            super(itemView);
            activityLabel = itemView.findViewById(R.id.activityLabel);
            timeSpinner = itemView.findViewById(R.id.timeSpinner);
            typeSpinner = itemView.findViewById(R.id.typeSpinner);

            // Set up listeners for type and time spinners
            typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // Handle type selection for this activity
                    String selectedType = parentView.getItemAtPosition(position).toString();

                    // Map the selectedType string to the corresponding ActivityType enum
                    ActivityType activityType = mapStringToActivityType(selectedType);

                    if (activityType != null) {
                        // Update the selected type for this activity in your data structure
                        ActivityOption activity = activities.get(getAdapterPosition());
                        activity.setSelectedType(activityType);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Do nothing
                }
            });


            timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // Handle time selection for this activity
                    String selectedTime = parentView.getItemAtPosition(position).toString();

                    // Update the selected time for this activity in your data structure
                    ActivityOption activity = activities.get(getAdapterPosition());
                    activity.setSelectedTime(selectedTime);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Do nothing
                }
            });

        }
        private ActivityType mapStringToActivityType(String selectedType) {
            switch (selectedType) {
                case "Restaurant":
                    return ActivityType.RESTAURANT;
                case "Entertainment":
                    return ActivityType.ENTERTAINMENT;
                case "Nightlife":
                    return ActivityType.NIGHTLIFE;
                case "Outdoors":
                    return ActivityType.OUTDOORS;
                default:
                    return ActivityType.NONE;
            }
        }
    }

}
