package edu.polytech.ebudget.datamodels.notiffactory;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.polytech.ebudget.datamodels.FirebasePaths;

public class NotifPhone implements INotification{
    String description;
    String user;
    IThreshold threshold;
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static final String TAG = "AddNotification";

    public NotifPhone(String description, String user, IThreshold threshold){
        this.threshold = threshold;
        this.description = description;
        this.user = user;
    }

    @Override
    public IThreshold getThreshold() {
        return threshold;
    }

    @Override
    public String getDescription() {
        return description;
    }

    //TODO add method to generate image

    @Override
    public void addToDatabase() {
        Map<String, Object> notif = new HashMap<>();
        notif.put("category", this.threshold.getCategory());
        notif.put("date", this.threshold.firstReached());
        notif.put("description", description);
        notif.put("user", user);


        database.collection(FirebasePaths.notifications)
                .add(notif)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}
