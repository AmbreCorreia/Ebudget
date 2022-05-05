package edu.polytech.ebudget.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.polytech.ebudget.FragmentInCategory;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.categories.ICategoryAdapterListner;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.notifications.INotificationAdapterListener;

public class CategoryListAdapter extends ArrayAdapter<Category> {

    private Context context;
    private ICategoryAdapterListner listner;
    private int layoutResourceId;
    private static final String LOG_TAG = "CategoryListAdapter";

    public CategoryListAdapter(Context context, int textViewResourceId) {
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
            TextView percent = (TextView) v.findViewById(R.id.text_view_progress_category);
            ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progress_bar_category);

            name.setText(item.name);
            budget.setText(String.valueOf(item.budget));
            int progress = (int) ((float)(item.expense*100)/ (float)item.budget);
            percent.setText(String.valueOf(progress)+"%");
            progressBar.setProgress(progress);

            Button b = (Button) v.findViewById(R.id.buttonList);
            b.setOnClickListener(click -> {
                listner.onClickCategory(item, position);
            });

            return v;

        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
    }

    public void addListner(ICategoryAdapterListner listner){
        this.listner = listner;
    }
}
