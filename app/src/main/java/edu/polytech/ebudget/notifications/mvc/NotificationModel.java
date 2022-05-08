package edu.polytech.ebudget.notifications.mvc;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class NotificationModel extends Observable {
    private final String TAG = "notifications " + getClass().getSimpleName();
    public FirebaseFirestore database = FirebaseFirestore.getInstance();
    public String category = "";
    private Date date = new Date();
    public String description = "";
    public String user = "";
    public String id = "";
    public int threshold = 0;
    private NotificationController controller = null;

    public void setController(NotificationController controller) {
        this.controller = controller;
    }

    public NotificationModel(NotificationController controller){
        super();
        this.date = new Date();
        this.controller = controller;
        Log.d(TAG, "Model is created");
    }

    public NotificationModel(){

    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void addToDatabase(){
        Map<String, Object> notif = new HashMap<>();
        notif.put("category", category);
        notif.put("date", new Date());
        notif.put("description", description);
        notif.put("user", user);
        notif.put("id", id);

        database.collection("notifications").document(id)
                .set(notif)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + id))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

        setChanged();
        notifyObservers();
    }

    public void deleteFromDatabase(){
        database.collection("notifications").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

        setChanged();
        notifyObservers();
    }


    public static Comparator<NotificationModel> sortByCategory = new Comparator<NotificationModel>() {
        @Override
        public int compare(NotificationModel o1, NotificationModel o2) {
            String n1 = o1.category.toLowerCase();
            String n2 = o2.category.toLowerCase();
            return n1.compareTo(n2);
        }
    };

    public static Comparator<NotificationModel> sortByDate= new Comparator<NotificationModel>() {
        @Override
        public int compare(NotificationModel o1, NotificationModel o2) {
            Date d1 = o1.date;
            Date d2 = o2.date;
            return (-1) * d1.compareTo(d2);
        }
    };

}
