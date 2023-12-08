package edu.northeastern.groupproject_outandabout.util;

import android.content.Context;
import android.content.Intent;
import edu.northeastern.groupproject_outandabout.ui.plan.Plan;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityBuilderSlot;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

public class PlanExportUtil {

    // Converts the Plan object into a shareable text format
    public static String convertPlanToText(Plan plan) {
        StringBuilder sb = new StringBuilder();
        sb.append("Plan Name: ").append(plan.getName()).append("\n");

        // Extract ActivityBuilderSlot details
        sb.append("Activity Builder Slots:\n");
        for (ActivityBuilderSlot slot : plan.getActivitySlots()) {
            sb.append(" - ").append(slot.toString()).append("\n");
        }

        // Extract ActivityOption details
        sb.append("Selected Activities:\n");
        for (ActivityOption option : plan.getSelectedActivities()) {
            sb.append(" - ").append(option.toString()).append("\n");
        }

        // Extract location details
        sb.append("Location: Latitude = ").append(plan.getLatitude())
                .append(", Longitude = ").append(plan.getLongitude()).append("\n");

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
