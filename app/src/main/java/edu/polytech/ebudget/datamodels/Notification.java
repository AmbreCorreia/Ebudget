package edu.polytech.ebudget.datamodels;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notification {

    public String category;
    private Date date;
    public String description;
    public String user;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static final String TAG = "AddNotification";


    public Notification(){}

    public Notification(String category, String description, String user){
        this.category = category;
        this.date = new Date();
        this.description = description;
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void addToDatabase(){
        Map<String, Object> notif = new HashMap<>();
        notif.put("category", this.category);
        notif.put("date", date);
        notif.put("description", description);
        notif.put("user", user);


        database.collection("notifications")
                .add(notif)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public void deleteFromDatabase(){

    }
}
