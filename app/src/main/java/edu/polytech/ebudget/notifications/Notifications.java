package edu.polytech.ebudget.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.Notification;

public class Notifications extends AppCompatActivity implements INotificationAdapterListener {

    private ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //ListeNotifications notifications = new ListeNotifications();

        FirebaseFirestore.getInstance().collection("notifications")
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        notifications = new ArrayList();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Notification notif = document.toObject(Notification.class);
                            notifications.add(notif);
                        }
                    }
                });

        NotificationsAdapter adapter = new NotificationsAdapter(getApplicationContext(), notifications);

        ListView list = findViewById(R.id.listView);

        list.setAdapter(adapter);

        adapter.addListner(this);
    }


    @Override
    public void onClickNotification(Notification notification){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("La supression sera définitive. Veuillez confirmer")
                .setNeutralButton("Annuler", null)
                .setNeutralButton("Supprimer", null);
        builder.show();
    }
}