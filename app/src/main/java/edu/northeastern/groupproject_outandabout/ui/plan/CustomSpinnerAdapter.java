package edu.northeastern.groupproject_outandabout.ui.plan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = 60;
        v.setLayoutParams(params);
        return v;
    }
}