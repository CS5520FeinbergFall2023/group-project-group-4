package edu.northeastern.groupproject_outandabout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * This class is an adapter for the swipable activity cards in the SwipeActivity class
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<ActivityOption> activityOptions;

    public CardAdapter(List<ActivityOption> activityOptions) {
        this.activityOptions = activityOptions;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ActivityOption activityOption = activityOptions.get(position);
        holder.bind(activityOption);
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
                    activityOption.getAddress() + "\n" +
                    "Rating: " + activityOption.getRating();

            infoTextView.setText(info);
        }
    }
}
