package edu.polytech.ebudget.datamodels;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notification implements Comparator {

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

    @Override
    public int compare(Object o1, Object o2) {
        Notification n1 = (Notification) o1;
        Notification n2 = (Notification) o2;
        int stringComp =
                n1.category.compareTo(n2.category);
        return stringComp;
    }

}
