package edu.northeastern.groupproject_outandabout;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * This class is an adapter for the chosen activity options in the OptionsActivity class
 */
public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.CardViewHolder> {

    private List<ActivityOption> activityOptions;
    private OnItemClickListener listener;

    public OptionAdapter(List<ActivityOption> activityOptions) {
        this.activityOptions = activityOptions;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ActivityOption activityOption = activityOptions.get(position);
        holder.bind(activityOption);

        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });

        if (activityOption.isSelected()) {
            holder.itemView.setBackgroundResource(R.drawable.selected_background);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return activityOptions.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView infoTextView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            infoTextView = itemView.findViewById(R.id.infoTextView);
        }

        public void bind(ActivityOption activityOption) {
            String info = activityOption.getName() + "\n" +
                    activityOption.getRating() + "\n" +
                    "Rating: " + activityOption.getAddress();

            infoTextView.setText(info);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}