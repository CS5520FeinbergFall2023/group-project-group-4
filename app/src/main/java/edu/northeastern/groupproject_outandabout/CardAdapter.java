package edu.northeastern.groupproject_outandabout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * This class is an adapter for the swipe-able activity cards in the SwipeActivity class
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<ActivityOption> activityOptions;
    private List<ActivityOption> savedActivities;
    private List<ActivityOption> removedActivities;
    private ButtonStateListener buttonStateListener;

    public interface ButtonStateListener {
        void onUpdateButtonState();
    }

    public CardAdapter(List<ActivityOption> activityOptions, List<ActivityOption> savedActivities, List<ActivityOption> removedActivities, ButtonStateListener listener) {
        this.activityOptions = activityOptions;
        this.savedActivities = savedActivities;
        this.removedActivities = removedActivities;
        this.buttonStateListener = listener;
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

    public List<ActivityOption> getActivityOptions() {
        return activityOptions;
    }

    public List<ActivityOption> getSavedActivities() {
        return savedActivities;
    }

    public List<ActivityOption> getRemovedActivities() {
        return removedActivities;
    }

    public void setActivityOptions(List<ActivityOption> activityOptions) {
        this.activityOptions = activityOptions;
    }

    public void setSavedActivities(List<ActivityOption> savedActivities) {
        this.savedActivities = savedActivities;
    }

    public void setRemovedActivities(List<ActivityOption> removedActivities) {
        this.removedActivities = removedActivities;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView infoTextView;
        private Button leftButton;
        private Button rightButton;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            infoTextView = itemView.findViewById(R.id.infoTextView);
            leftButton = itemView.findViewById(R.id.leftButton);
            rightButton = itemView.findViewById(R.id.rightButton);
        }

        public void bind(ActivityOption activityOption) {
            String info = activityOption.getName() + "\n" +
                    activityOption.getAddress() + "\n" +
                    "Rating: " + activityOption.getRating();

            infoTextView.setText(info);

            leftButton.setOnClickListener(v -> performSwipe(ItemTouchHelper.LEFT));
            rightButton.setOnClickListener(v -> performSwipe(ItemTouchHelper.RIGHT));
        }

        private void performSwipe(int direction) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Trigger the swipe action programmatically with the correct adapter reference
                new ItemTouchHelperCallback(direction, CardAdapter.this).onSwiped(this, direction);
            }
        }
    }

    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        private int swipeDirection;
        private CardAdapter adapter;

        public ItemTouchHelperCallback(int swipeDirection, CardAdapter adapter) {
            this.swipeDirection = swipeDirection;
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, swipeDirection);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            if (position < activityOptions.size()) {
                ActivityOption swipedItem = activityOptions.remove(position);

                if (direction == ItemTouchHelper.LEFT) {
                    removedActivities.add(swipedItem);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    savedActivities.add(swipedItem);
                }

                activityOptions.add(0, swipedItem);
                adapter.notifyItemMoved(position, 0);

                updateButtonState();
            }
        }
    }

    private void updateButtonState() {
        if (buttonStateListener != null) {
            buttonStateListener.onUpdateButtonState();
        }
    }
}
