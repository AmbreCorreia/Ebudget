package edu.polytech.ebudget.datamodels;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Preference {

    public String user;
    public boolean notifictionEnabled;
    public int calendarID;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static final String TAG = "AddCalendar";

    public Preference(){
        //requiered
    }

    public Preference(String user, boolean notifictionEnabled, int calendarID){
        this.user = user;
        this.notifictionEnabled = notifictionEnabled;
        this.calendarID = calendarID;
    }

    public void addToDatabase(){
        Map<String, Object> preference = new HashMap<>();
        preference.put("user", user);
        preference.put("notificationEnabled", notifictionEnabled);
        preference.put("calendarID", calendarID);

        database.collection(FirebasePaths.preferences).document(user)
                .set(preference)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added"))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    @Override
    public String toString(){
        return "user: "+ user + " notif: "+ String.valueOf(notifictionEnabled) + " calendarID: " + String.valueOf(calendarID);
    }
}
