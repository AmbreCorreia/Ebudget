package edu.polytech.ebudget.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import java.util.Objects;

public class ApplicationDemo extends Application {
    public static final String CHANNEL_ID = "channel1";

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    private static NotificationManager notificationManager;

    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannel("NomChannel", "DescriptionChannel", NotificationManager.IMPORTANCE_HIGH);
    }

    private void createNotificationChannel(String name, String description, int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            //peut-etre fait qu'une seule, ne pourra plus être changé après
            notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);
        }
    }
}
