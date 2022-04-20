package edu.polytech.ebudget.notifications;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import edu.polytech.ebudget.R;

public class Notifications extends AppCompatActivity implements INotificationAdapterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ListeNotifications notifications = new ListeNotifications();
        NotificationsAdapter adapter = new NotificationsAdapter(getApplicationContext(), notifications);

        ListView list = findViewById(R.id.listView);

        list.setAdapter(adapter);

        adapter.addListner(this);
    }

    @Override
    public void onClickNotification(MyNotification notification){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("La supression sera définitive. Veuillez confirmer")
                .setNeutralButton("Annuler", null)
                .setNeutralButton("Supprimer", null);
        builder.show();
    }
}