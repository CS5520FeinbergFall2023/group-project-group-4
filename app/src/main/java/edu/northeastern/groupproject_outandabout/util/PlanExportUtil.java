package edu.northeastern.groupproject_outandabout.util;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import edu.northeastern.groupproject_outandabout.ui.plan.Plan;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityBuilderSlot;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

public class PlanExportUtil {

    // Converts the Plan object into a shareable text format
    public static String convertPlanToText(Plan plan) {

        // using string builder to create plan string
        StringBuilder sb = new StringBuilder();

        // Extract Plan name
        sb.append("Plan Name: ").append(plan.getName()).append("\n");

        // Extract ActivityBuilderSlot details
        /*
        sb.append("Activity Builder Slots:\n");
        for (ActivityBuilderSlot slot : plan.getActivitySlots()) {
            sb.append(" - ").append(slot.toString()).append("\n");
        }
        */

        // Extract ActivityOption details
        /*
        sb.append("Selected Activities:\n");
        for (ActivityOption option : plan.getSelectedActivities()) {
            sb.append(" - ").append(option.toString()).append("\n");
        }
        */
        List<ActivityOption> selectedActivities = plan.getSelectedActivities();
        for (int i = 0; i < selectedActivities.size(); i++) {
            sb.append("*** Activity ").append(i+1).append(" ***\n");
            sb.append(selectedActivities.get(i).toString());
        }

        // Extract location details
        /*
        sb.append("Location: Latitude = ").append(plan.getLatitude())
                .append(", Longitude = ").append(plan.getLongitude()).append("\n");
        */
        return sb.toString();
    }

    // Initiates a share intent with the given text
    public static void sharePlan(Context context, String planDetails) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, planDetails);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Share plan with");
        context.startActivity(shareIntent);
    }
}
