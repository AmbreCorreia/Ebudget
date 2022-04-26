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

import java.text.DateFormat;
import java.util.ArrayList;

import edu.polytech.ebudget.FragmentInCategory;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.Item;

public class ExpenseListAdapter extends ArrayAdapter<Item> {

    private Context context;
    private int layoutResourceId;
    private static final String LOG_TAG = "ExpenseListAdapter";

    public ExpenseListAdapter(Context context, int textViewResourceId) {
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

            TextView name = (TextView) v.findViewById(R.id.itemname);
            TextView category = (TextView) v.findViewById(R.id.itemcategory);
            TextView price = (TextView) v.findViewById(R.id.itemprice);
            TextView date = (TextView) v.findViewById(R.id.itemdate);

            name.setText(item.name);
            category.setText(item.category);
            price.setText(String.valueOf(item.price));
            date.setText(DateFormat.getDateInstance().format(item.date));

            return v;

        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
    }
}