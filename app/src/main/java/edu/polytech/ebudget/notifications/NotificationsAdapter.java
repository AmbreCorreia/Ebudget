package edu.polytech.ebudget.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Notification;

public class NotificationsAdapter extends BaseAdapter {
    private final ArrayList<Notification> notifications;
    private INotificationAdapterListener listner;
    private LayoutInflater mInflater;

    public NotificationsAdapter(Context context, ArrayList<Notification> notifications){
        mInflater = LayoutInflater.from(context);
        this.notifications = notifications;
    }

    public int getCount(){ return this.notifications.size(); }
    public Object getItem(int position){ return this.notifications.get(position); }
    public long getItemId(int position){ return position; }

    public View getView(int position, View convertView, ViewGroup parent){

        LinearLayout layoutItem;
        layoutItem = (LinearLayout) (convertView == null ? mInflater.inflate(R.layout.notif_layout, parent, false) : convertView);

        TextView tvCategory = layoutItem.findViewById(R.id.notifCategory);
        TextView tvDescription = layoutItem.findViewById((R.id.notifDescription));

        tvCategory.setText(notifications.get(position).getCategory());
        tvDescription.setText(notifications.get(position).getDescription());

        layoutItem.setOnClickListener(click -> {
            listner.onClickNotification(notifications.get(position));
        });

        return layoutItem;

    }

    public void addListner(INotificationAdapterListener listner){
        this.listner = listner;
    }
}
