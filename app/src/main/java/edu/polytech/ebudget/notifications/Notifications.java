package edu.polytech.ebudget.notifications;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
/*
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

 */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Notification;

public class Notifications extends AppCompatActivity implements INotificationAdapterListener {

    ArrayList<Notification> doc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        NotificationsAdapter adapter = new NotificationsAdapter(getApplicationContext(), R.layout.notif_layout);
        ListView list = findViewById(R.id.listView);
        list.setAdapter(adapter);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        Spinner spinnerTri = (Spinner)findViewById(R.id.spinnerTri);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,spinnerArray);
        spinnerArrayAdapter.add("Date");
        spinnerArrayAdapter.add("Catégorie");
        spinnerTri.setAdapter(spinnerArrayAdapter);

        list.setAdapter(adapter);
        adapter.addListner(this);

        FirebaseFirestore.getInstance().collection(FirebasePaths.notifications)
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
                        doc = documents;
                    }
                });

        Button okTri = (Button)findViewById(R.id.ok_tri);
        okTri.setOnClickListener(view -> {
            String typeTri = spinnerTri.getSelectedItem().toString();
            switch(typeTri){
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
    public void onClickNotification(Notification notification, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("La supression de : " + notification.getCategory() + ", sera définitive. Veuillez confirmer")
                .setNeutralButton("Annuler", null)
                .setNeutralButton("Supprimer", (dialogInterface, i) -> {
                    System.out.println("click sur supprimer");
                    FirebaseFirestore.getInstance().collection(FirebasePaths.notifications).
                            document(notification.id)
                            .delete()
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                            .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
                });
        builder.show();
    }
}