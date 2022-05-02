package edu.polytech.ebudget.notifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import edu.polytech.ebudget.R;

public class ViewAdapter extends BaseAdapter {

    private final String TAG = "frallo " + getClass().getSimpleName();
    private LayoutInflater inflater;
    private NotificationModel modelNotification;
    private NotificationView viewNotification;


    public <T extends ViewGroup> ViewAdapter(Context context, NotificationView view) {
        inflater = LayoutInflater.from(context);
        this.viewNotification = view;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ConstraintLayout layoutItem;

        //(1) : Réutilisation des layouts
        layoutItem = (ConstraintLayout) (view == null ? inflater.inflate(R.layout.notif_layout, parent, false) : view);

        //(2) : Récupération des TextView de notre layout
        TextView category = layoutItem.findViewById(R.id.notifCategory);
        TextView description = layoutItem.findViewById(R.id.notifDescription);

        //(3) : Renseignement des valeurs
        category.setText( modelNotification.category );
        description.setText(modelNotification.description);

        //écouter si clic sur la vue
        layoutItem.setOnClickListener( click ->  viewNotification.onClickNotification(modelNotification, position));

        //On retourne l'item créé.
        return layoutItem;
    }

    public void refresh(NotificationModel model) {
        updateModel(model);
        Log.d(TAG, "listView refreshed with ==> " + model);
        //todo check team==TEAM1
        notifyDataSetChanged();     //refresh display
    }

    public void updateModel(NotificationModel model) {
        this.modelNotification = model;
    }
}
