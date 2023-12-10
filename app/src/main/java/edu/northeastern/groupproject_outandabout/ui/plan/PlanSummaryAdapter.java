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
        // CustomSpinnerAdapter for time spinner
        CustomSpinnerAdapter timeAdapter = new CustomSpinnerAdapter(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item,
                holder.itemView.getContext().getResources().getStringArray(R.array.time_options));
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.timeSpinner.setAdapter(timeAdapter);


        // AM/PM spinner setup
        ArrayAdapter<CharSequence> ampmAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.ampm_options, android.R.layout.simple_spinner_item);
        ampmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.ampmSpinner.setAdapter(ampmAdapter);

        // Type spinner setup
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

        String ampmString = slot.getAmpm();
        int ampmPosition = ampmAdapter.getPosition(ampmString);
        holder.ampmSpinner.setSelection(ampmPosition >= 0 ? ampmPosition : 0);

        holder.ampmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAmpm = parent.getItemAtPosition(position).toString();
                slot.setAmpm(selectedAmpm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
        Spinner ampmSpinner;

        PlanViewHolder(View itemView) {
            super(itemView);
            activityLabel = itemView.findViewById(R.id.activityLabel);
            timeSpinner = itemView.findViewById(R.id.timeSpinner);
            typeSpinner = itemView.findViewById(R.id.typeSpinner);
            ampmSpinner = itemView.findViewById(R.id.ampmSpinner);
        }
    }

}

