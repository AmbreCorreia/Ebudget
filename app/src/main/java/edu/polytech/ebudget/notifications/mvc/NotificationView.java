package edu.polytech.ebudget.notifications.mvc;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

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

public class NotificationView implements Observer{

    private final String TAG = "notifications " + getClass().getSimpleName();
    private View view;
    ArrayList<NotificationModel> doc = new ArrayList<>();
    private boolean modelCreated = false;
    NotificationController controller;
    ViewAdapter adapter;
    Context context;

    public NotificationView(Context context, View view) {
        this.view = view;
        adapter = new ViewAdapter(context, R.layout.notif_layout);
        this.context = context;
    }

    public View getView() {
        return view;
    }
    public void setListener() {

    }


    public void onClickNotification(NotificationModel model, int position){
        controller.onClickNotification(model, position);
    }

    @Override
    public void update(Observable observable, Object o) {
        NotificationModel model = (NotificationModel) observable;
        if (observable.hasChanged()) {
            //adapter.updateModel(model);
            ListView list = view.findViewById(R.id.listView);
            list.setAdapter(adapter);

            ArrayList<String> spinnerArray = new ArrayList<String>();
            Spinner spinnerTri = (Spinner)view.findViewById(R.id.spinnerTri);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item,spinnerArray);
            spinnerArrayAdapter.add("Date");
            spinnerArrayAdapter.add("Catégorie");
            spinnerTri.setAdapter(spinnerArrayAdapter);

            list.setAdapter(adapter);
            adapter.addListner(controller);

            FirebaseFirestore.getInstance().collection(FirebasePaths.notifications)
                    .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<NotificationModel> documents = new ArrayList();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                NotificationModel notif = document.toObject(NotificationModel.class);
                                documents.add(notif);
                            }
                            adapter.addAll(documents);
                            doc = documents;
                        }
                    });

            Button okTri = (Button)view.findViewById(R.id.ok_tri);
            okTri.setOnClickListener(click -> {
                String typeTri = spinnerTri.getSelectedItem().toString();
                switch(typeTri){
                    case "Catégorie":
                        Collections.sort(doc, NotificationModel.sortByCategory);
                        adapter.clear();
                        adapter.addAll(doc);
                        break;

                    case "Date":
                        Collections.sort(doc, NotificationModel.sortByDate);
                        adapter.clear();
                        adapter.addAll(doc);
                        break;

                    default:
                        break;
                }
            });
            modelCreated = true;
        }
        else {
            adapter.refresh(model);
        }
        Log.d(TAG, "View update with ==> " + model);
    }
}
