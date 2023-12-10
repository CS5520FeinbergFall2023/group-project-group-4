package edu.northeastern.groupproject_outandabout;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        private TextView addressTextView;
        private TextView nameTextView;
        private TextView ratingTextView;
        private ImageView iconImage;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            iconImage = itemView.findViewById(R.id.activityIcon);
        }

        public void bind(ActivityOption activityOption) {
            String name = activityOption.getName();
            String rating = "Rating: " + activityOption.getRating();
            String address = activityOption.getAddress();

            if (activityOption.getSelectedType() == ActivityType.RESTAURANT) {
                Drawable restaurantDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.restaurant);
                iconImage.setImageDrawable(restaurantDrawable);
            } else if (activityOption.getSelectedType() == ActivityType.ENTERTAINMENT) {
                Drawable restaurantDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.entertainment);
                iconImage.setImageDrawable(restaurantDrawable);
            } else if (activityOption.getSelectedType() == ActivityType.NIGHTLIFE) {
                Drawable restaurantDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.nightlife);
                iconImage.setImageDrawable(restaurantDrawable);
            } else if (activityOption.getSelectedType() == ActivityType.OUTDOORS) {
                Drawable restaurantDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.outdoors);
                iconImage.setImageDrawable(restaurantDrawable);
            }

            nameTextView.setText(name);
            ratingTextView.setText(rating);
            addressTextView.setText(address);
        }
    }
}