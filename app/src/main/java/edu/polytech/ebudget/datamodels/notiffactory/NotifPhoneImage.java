package edu.polytech.ebudget.datamodels.notiffactory;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.notiffactory.INotification;
import edu.polytech.ebudget.datamodels.notiffactory.IThreshold;
import edu.polytech.ebudget.datamodels.notiffactory.ThresholdValue;
import edu.polytech.ebudget.notifications.ApplicationDemo;
import edu.polytech.ebudget.notifications.mvc.NotificationController;
import edu.polytech.ebudget.notifications.mvc.NotificationModel;

public class NotifPhoneImage extends Observable implements INotification {
    private final String TAG = "notifications " + getClass().getSimpleName();
    public FirebaseFirestore database = FirebaseFirestore.getInstance();
    private Date date = new Date();
    public String description = "";
    public String user = "";
    public String id = "";
    public ThresholdValue threshold;
    private NotificationController controller = null;



    public NotifPhoneImage(NotificationController controller){
        super();
        this.date = new Date();
        this.controller = controller;
        Log.d(TAG, "Model is created");
    }

    public NotifPhoneImage(){
    }

    public NotifPhoneImage(NotificationController controller, String description, String id, String user, ThresholdValue threshold){
        this.controller = controller;
        this.description = description;
        this.id = id;
        this.user = user;
        this.threshold = threshold;
    }

    public void setController(NotificationController controller) {
        this.controller = controller;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        threshold.setCategory(category);
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setThreshold(ThresholdValue threshold) {
        this.threshold = threshold;
    }

    public String getCategory() {
        return threshold.getCategory();
    }

    public void addToDatabase(){
        Map<String, Object> notif = new HashMap<>();
        notif.put("category", getCategory());
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

    public void sendNotif(NotificationCompat.Builder notification, int notificationId, Resources ressources, int image) {
        notification.setSmallIcon(R.drawable.ic_baseline_apps_24)
                .setContentTitle("Attention!")
                .setContentText("Vous avez dépassé votre budget")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(ressources, image)));
        ApplicationDemo.getNotificationManager().notify(notificationId, notification.build());

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



    public static Comparator<NotifPhoneImage> sortByCategory = new Comparator<NotifPhoneImage>() {
        @Override
        public int compare(NotifPhoneImage o1, NotifPhoneImage o2) {
            String n1 = o1.getCategory().toLowerCase();
            String n2 = o2.getCategory().toLowerCase();
            return n1.compareTo(n2);
        }
    };

    public static Comparator<NotifPhoneImage> sortByDate= new Comparator<NotifPhoneImage>() {
        @Override
        public int compare(NotifPhoneImage o1, NotifPhoneImage o2) {
            Date d1 = o1.date;
            Date d2 = o2.date;
            return (-1) * d1.compareTo(d2);
        }
    };

}
