package edu.polytech.ebudget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.polytech.ebudget.datamodels.Category;

public class CategoryListAdapter extends ArrayAdapter<Category> {
    private int layoutResourceId;
    private static final String LOG_TAG = "CategoryListAdapter";

    public CategoryListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        layoutResourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Category item = getItem(position);
            View v = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = inflater.inflate(layoutResourceId, null);

            } else {
                v = convertView;
            }

//            TextView name = (TextView) v.findViewById(R.id.display_name);
//            TextView budget = (TextView) v.findViewById(R.id.display_budget);

//            name.setText(item.name);
//            budget.setText(item.budget);

            return v;

        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
    }
}
