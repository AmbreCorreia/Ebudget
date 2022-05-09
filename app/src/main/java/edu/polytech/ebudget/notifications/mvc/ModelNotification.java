package edu.polytech.ebudget.notifications.mvc;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Observable;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Notification;

public class ModelNotification extends Observable {
    private final String TAG = "notifications " + getClass().getSimpleName();
    public FirebaseFirestore database = FirebaseFirestore.getInstance();
    private ControllerNotification controller = null;

    public ModelNotification(FirebaseFirestore database){
        this.database = database;
        Log.d(TAG, "Model is created");
    }

    public void setController(ControllerNotification controller) {
        this.controller = controller;
    }

    public Object get(Notification notification) {
        return database.collection("notifications").document(notification.id);
    }

    public void deleteNotification(Notification notification){
        database.collection(FirebasePaths.notifications).
                document(notification.id)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
        modelHasChanged();
    }

    public void modelHasChanged(){
        controller.modelChanged = true;
        setChanged();
        notifyObservers();
    }

}
