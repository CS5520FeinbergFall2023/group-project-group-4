package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Context;
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
        setupSpinners(holder, slot, position);
    }

    private void setupSpinners(PlanViewHolder holder, ActivityBuilderSlot slot, int position) {
        Context context = holder.itemView.getContext();

        // Time spinner setup
        setupTimeSpinner(context, holder, slot);

        // AM/PM spinner setup
        setupAmpmSpinner(context, holder, slot);

        // Type spinner setup
        setupTypeSpinner(context, holder, slot, position);
    }

    private void setupTimeSpinner(Context context, PlanViewHolder holder, ActivityBuilderSlot slot) {
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(context, R.array.time_options, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.timeSpinner.setAdapter(timeAdapter);
        int timePosition = timeAdapter.getPosition(slot.getTimeslot());
        holder.timeSpinner.setSelection(timePosition >= 0 ? timePosition : 0);

        holder.timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                slot.setTimeslot(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupAmpmSpinner(Context context, PlanViewHolder holder, ActivityBuilderSlot slot) {
        ArrayAdapter<CharSequence> ampmAdapter = ArrayAdapter.createFromResource(context, R.array.ampm_options, android.R.layout.simple_spinner_item);
        ampmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.ampmSpinner.setAdapter(ampmAdapter);
        int ampmPosition = ampmAdapter.getPosition(slot.getAmpm());
        holder.ampmSpinner.setSelection(ampmPosition >= 0 ? ampmPosition : 0);

        holder.ampmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                slot.setAmpm(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupTypeSpinner(Context context, PlanViewHolder holder, ActivityBuilderSlot slot, int position) {
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(context, R.array.type_options, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.typeSpinner.setAdapter(typeAdapter);
        holder.typeSpinner.setSelection(getTypePosition(typeAdapter, slot));

        holder.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int selectedPosition, long id) {
                ActivityType selectedType = convertStringToActivityType(typeAdapter.getItem(selectedPosition).toString(), context);
                activitySlots.get(position).setType(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private int getTypePosition(ArrayAdapter<CharSequence> typeAdapter, ActivityBuilderSlot slot) {
        String typeString = slot.getType().toString();
        return typeAdapter.getPosition(typeString);
    }

    private ActivityType convertStringToActivityType(String typeString, Context context) {
        String[] typeOptions = context.getResources().getStringArray(R.array.type_options);
        if (typeString.equals(typeOptions[0])) {
            return ActivityType.RESTAURANT;
        } else if (typeString.equals(typeOptions[1])) {
            return ActivityType.ENTERTAINMENT;
        } else if (typeString.equals(typeOptions[2])) {
            return ActivityType.NIGHTLIFE;
        } else if (typeString.equals(typeOptions[3])) {
            return ActivityType.OUTDOORS;
        }
        return ActivityType.NONE;
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
