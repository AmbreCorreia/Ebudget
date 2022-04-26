package edu.polytech.ebudget.notifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Notification;

public class NotificationsAdapter extends ArrayAdapter<Notification> {


    private Context context;
    private INotificationAdapterListener listner;
    private int layoutResourceId;
    private static final String LOG_TAG = "NotificationsAdapter";

    public NotificationsAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
        this.context = context;
        this.layoutResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        try {
            Notification item = getItem(position);
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
                listner.onClickNotification(item, position);
            });

            /*b.setOnClickListener(click -> {
                Bundle bundle = new Bundle();
                bundle.putString("param1", item.name);
                FragmentInCategory frag = new FragmentInCategory();
                frag.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, frag);
                fragmentTransaction.commit();
            });*/

            return v;

        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }

    }

    public void addListner(INotificationAdapterListener listner){
        this.listner = listner;
    }
}
