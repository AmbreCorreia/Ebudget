package edu.polytech.ebudget.datamodels;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import edu.polytech.ebudget.MainActivity;
import edu.polytech.ebudget.notifications.Notifications;

public class Notification {

    public String category;
    private Date date;
    public String description;
    public String user;
    public String id;
    public int threshold;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static final String TAG = "AddNotification";

    public Notification(){}

    public Notification(String category, String description, String user, String id, int thres){
        this.category = category;
        this.date = new Date();
        this.description = description;
        this.user = user;
        this.id = id;
        this.threshold = thres;
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

    public String getId(){ return id; }

    public void addToDatabase(){
        Map<String, Object> notif = new HashMap<>();
        notif.put("category", this.category);
        notif.put("date", date);
        notif.put("description", description);
        notif.put("user", user);
        notif.put("id", id);
        notif.put("threshold", threshold);


        database.collection(FirebasePaths.notifications).document(id)
                .set(notif)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + id))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public static Comparator<Notification> sortByCategory = new Comparator<Notification>() {
        @Override
        public int compare(Notification o1, Notification o2) {
            String n1 = o1.category.toLowerCase();
            String n2 = o2.category.toLowerCase();
            return n1.compareTo(n2);
        }
    };

    public static Comparator<Notification> sortByDate= new Comparator<Notification>() {
        @Override
        public int compare(Notification o1, Notification o2) {
            Date d1 = o1.date;
            Date d2 = o2.date;
            return (-1) * d1.compareTo(d2);
        }
    };



}
