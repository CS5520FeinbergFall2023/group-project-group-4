package edu.northeastern.groupproject_outandabout.ui.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.groupproject_outandabout.R;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.PlanViewHolder> {

    private List<Plan> plans;
    private OnPlanClickListener listener;

    public interface OnPlanClickListener {
        void onPlanClick(Plan plan);
    }

    public PlanListAdapter(List<Plan> plans, OnPlanClickListener listener) {
        this.plans = plans;
        this.listener = listener;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        Plan plan = plans.get(position);
        holder.planNameTextView.setText(plan.getName());
        holder.itemView.setOnClickListener(v -> listener.onPlanClick(plan));
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    static class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView planNameTextView;

        PlanViewHolder(View itemView) {
            super(itemView);
            planNameTextView = itemView.findViewById(R.id.planNameTextView);
        }
    }
}
