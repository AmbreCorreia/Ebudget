package edu.polytech.ebudget.notifications.mvc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.notifications.mvc.NotificationController;
import edu.polytech.ebudget.notifications.mvc.NotificationModel;

public class ViewAdapter extends ArrayAdapter<NotificationModel> {

    private final String LOG_TAG = "NotificationsAdapter" + getClass().getSimpleName();
    private LayoutInflater inflater;
    private Context context;
    private NotificationModel notificationModel;
    private NotificationController listner;
    private NotificationView notificationView;
    private int layoutResourceId;

    public ViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
        this.layoutResourceId = textViewResourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            NotificationModel item = getItem(position);
            View v = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(layoutResourceId, null);

            } else {
                v = convertView;
            }

            TextView notifCategory = (TextView) v.findViewById(R.id.notifCategory);
            TextView notifDescription = (TextView) v.findViewById(R.id.notifDescription);

            notifCategory.setText(item.category);
            notifDescription.setText(String.valueOf(item.description));

            ImageButton b = (ImageButton) v.findViewById(R.id.delete_notif);
            b.setOnClickListener(click ->{
                notificationView.onClickNotification(item, position);
            });

            return v;

        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }

        /*
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

         */
    }

    public void addListner(NotificationController listner){
        this.listner = listner;
    }

    public void refresh(NotificationModel model) {
        updateModel(model);
        Log.d(LOG_TAG, "listView refreshed with ==> " + model);
        //todo check team==TEAM1
        notifyDataSetChanged();     //refresh display
    }

    public void updateModel(NotificationModel model) {
        this.notificationModel = model;
    }
}
