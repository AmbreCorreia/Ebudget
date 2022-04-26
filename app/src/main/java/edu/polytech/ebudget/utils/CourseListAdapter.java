package edu.polytech.ebudget.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Item;

public class CourseListAdapter extends ArrayAdapter<Item> {
    private Context context;
    private int layoutResourceId;
    private static final String LOG_TAG = "CourseListAdapter";

    public CourseListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        layoutResourceId = textViewResourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Item item = getItem(position);
            View v = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = inflater.inflate(layoutResourceId, null);

            } else {
                v = convertView;
            }

            TextView name = (TextView) v.findViewById(R.id.itemlistcourse);
            name.setText(item.name);

            return v;

        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
    }
}
