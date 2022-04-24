package edu.polytech.ebudget.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.polytech.ebudget.FragmentInCategory;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Category;

public class CalendarListAdapter extends ArrayAdapter<Category> {

    private Context context;
    private int layoutResourceId;
    private static final String LOG_TAG = "CategoryListAdapter";

    public CalendarListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        layoutResourceId = textViewResourceId;
        this.context = context;
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

            TextView name = (TextView) v.findViewById(R.id.display_name);
            TextView budget = (TextView) v.findViewById(R.id.display_budget);

            name.setText(item.name);
            budget.setText(String.valueOf(item.budget));

            Button b = (Button) v.findViewById(R.id.buttonList);
            b.setOnClickListener(click -> {
                Bundle bundle = new Bundle();
                bundle.putString("param1", item.name);
                FragmentInCategory frag = new FragmentInCategory();
                frag.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, frag);
                fragmentTransaction.commit();
            });

            return v;

        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
    }
}