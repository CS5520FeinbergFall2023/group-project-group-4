package edu.northeastern.groupproject_outandabout.util;


import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.northeastern.groupproject_outandabout.R;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

public class PlanAdapter extends RecyclerView.Adapter<PlanViewHolder> {
    private List<FirebaseDatabaseUtil.SimplePlan> plans;

    public PlanAdapter(List<FirebaseDatabaseUtil.SimplePlan> plans) {
        this.plans = plans;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        FirebaseDatabaseUtil.SimplePlan plan = plans.get(position);
        holder.planNameTextView.setText(plan.getName());

        SpannableStringBuilder activitiesStringBuilder = new SpannableStringBuilder();
        int activityNumber = 1;
        for (ActivityOption activity : plan.getSelectedActivities()) {
            String activityName = "Activity " + activityNumber + ": ";
            String activityDetail = activity.getName() + "\n";
            String addressLabel = "Address: ";
            String addressDetail = activity.getAddress() + "\n";
            String selectedTime = "Selected Time: " + activity.getSelectedTime() + "\n\n";

            activitiesStringBuilder.append(activityName);
            int activityNameEnd = activitiesStringBuilder.length();
            activitiesStringBuilder.append(activityDetail);

            activitiesStringBuilder.append(addressLabel);
            int addressStart = activitiesStringBuilder.length();
            activitiesStringBuilder.append(addressDetail);
            int addressEnd = activitiesStringBuilder.length();


            ClickableSpan activityClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }
            };
            activitiesStringBuilder.setSpan(activityClickableSpan, activityNameEnd, activityNameEnd + activityDetail.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ClickableSpan addressClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(activity.getAddress()));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
                        view.getContext().startActivity(mapIntent);
                    }
                }
            };
            activitiesStringBuilder.setSpan(addressClickableSpan, addressStart, addressEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            activitiesStringBuilder.append(selectedTime);

            activityNumber++;
        }

        holder.activitiesTextView.setText(activitiesStringBuilder);
        holder.activitiesTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }



    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void updatePlans(List<FirebaseDatabaseUtil.SimplePlan> newPlans) {
        plans.clear();
        plans.addAll(newPlans);
        notifyDataSetChanged();
    }

}
