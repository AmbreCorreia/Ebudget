package edu.polytech.ebudget.notifications.mvc;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Notification;

public class ViewNotification extends AppCompatActivity implements Observer {

    ControllerNotification controllerNotification;
    ArrayList<Notification> doc = new ArrayList<>();

    public void setListner(ControllerNotification controllerNotification) {
        this.controllerNotification = controllerNotification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Log.d(TAG, "View is created");

        NotificationAdapter adapter = new NotificationAdapter(getApplicationContext(), R.layout.notif_layout);
        ListView list = findViewById(R.id.listView);
        list.setAdapter(adapter);
        adapter.addListner(controllerNotification);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        Spinner spinnerTri = (Spinner) findViewById(R.id.spinnerTri);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.add("Date");
        spinnerArrayAdapter.add("Catégorie");
        spinnerTri.setAdapter(spinnerArrayAdapter);

        //list.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection(FirebasePaths.notifications)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Notification> documents = new ArrayList();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Notification notif = document.toObject(Notification.class);
                            documents.add(notif);
                        }
                        adapter.addAll(documents);
                        doc = documents;
                    }
                });

        Button okTri = (Button) findViewById(R.id.ok_tri);
        okTri.setOnClickListener(view -> {
            String typeTri = spinnerTri.getSelectedItem().toString();
            switch (typeTri) {
                case "Catégorie":
                    Collections.sort(doc, Notification.sortByCategory);
                    adapter.clear();
                    adapter.addAll(doc);
                    break;

                case "Date":
                    Collections.sort(doc, Notification.sortByDate);
                    adapter.clear();
                    adapter.addAll(doc);
                    break;

                default:
                    break;
            }
        });


    }

    @Override
    public void update(Observable observable, Object o) {
        NotificationAdapter adapter = new NotificationAdapter(getApplicationContext(), R.layout.notif_layout);
        ListView list = findViewById(R.id.listView);
        list.setAdapter(adapter);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        Spinner spinnerTri = (Spinner) findViewById(R.id.spinnerTri);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.add("Date");
        spinnerArrayAdapter.add("Catégorie");
        spinnerTri.setAdapter(spinnerArrayAdapter);

        list.setAdapter(adapter);
        adapter.addListner(controllerNotification);

        FirebaseFirestore.getInstance().collection(FirebasePaths.notifications)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Notification> documents = new ArrayList();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Notification notif = document.toObject(Notification.class);
                            documents.add(notif);
                        }
                        adapter.addAll(documents);
                        doc = documents;
                    }
                });

        Button okTri = (Button) findViewById(R.id.ok_tri);
        okTri.setOnClickListener(view -> {
            String typeTri = spinnerTri.getSelectedItem().toString();
            switch (typeTri) {
                case "Catégorie":
                    Collections.sort(doc, Notification.sortByCategory);
                    adapter.clear();
                    adapter.addAll(doc);
                    break;

                case "Date":
                    Collections.sort(doc, Notification.sortByDate);
                    adapter.clear();
                    adapter.addAll(doc);
                    break;

                default:
                    break;
            }
        });
    }
}
