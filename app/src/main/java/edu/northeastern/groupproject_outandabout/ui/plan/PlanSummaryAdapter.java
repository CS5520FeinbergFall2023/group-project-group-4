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

    private List<ActivityBuilderSlot> activitySlots;


    public PlanSummaryAdapter(List<ActivityBuilderSlot> activitySlots) {
        this.activitySlots = activitySlots;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        ActivityBuilderSlot slot = activitySlots.get(position);
        holder.activityLabel.setText("Activity " + (position + 1));
        setupSpinners(holder, slot);
    }

    private void setupSpinners(PlanViewHolder holder, ActivityBuilderSlot slot) {
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.time_options, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.timeSpinner.setAdapter(timeAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.type_options, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.typeSpinner.setAdapter(typeAdapter);

        // Set the current selection for type spinner
        String typeString = slot.getType().toString();
        int typePosition = typeAdapter.getPosition(typeString);
        holder.typeSpinner.setSelection(typePosition >= 0 ? typePosition : 0);

        // Set the current selection for time spinner
        int timePosition = timeAdapter.getPosition(slot.getTimeslot());
        holder.timeSpinner.setSelection(timePosition >= 0 ? timePosition : 0);

        holder.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTypeString = parent.getItemAtPosition(position).toString();
                ActivityType selectedType = ActivityType.valueOf(selectedTypeString);
                slot.setType(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTime = parent.getItemAtPosition(position).toString();
                slot.setTimeslot(selectedTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }


    @Override
    public int getItemCount() {
        return activitySlots.size();
    }

    public List<ActivityBuilderSlot> getSelectedSlots() {
        return new ArrayList<>(activitySlots);
    }

    static class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView activityLabel;
        Spinner timeSpinner;
        Spinner typeSpinner;

        PlanViewHolder(View itemView) {
            super(itemView);
            activityLabel = itemView.findViewById(R.id.activityLabel);
            timeSpinner = itemView.findViewById(R.id.timeSpinner);
            typeSpinner = itemView.findViewById(R.id.typeSpinner);
        }


    }

}

