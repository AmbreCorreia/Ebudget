package edu.polytech.ebudget.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //ListeNotifications notifications = new ListeNotifications();
        NotificationsAdapter adapter = new NotificationsAdapter(getApplicationContext(), R.layout.notif_layout);
        ListView list = findViewById(R.id.listView);
        list.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("notifications")
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Notification> documents = new ArrayList();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Notification notif = document.toObject(Notification.class);
                            documents.add(notif);
                        }

                        adapter.addAll(documents);
                    }
                });

        /*findViewById(R.id.delete_notif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNotification(adapter.getItem(1));
            }
        });*/


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