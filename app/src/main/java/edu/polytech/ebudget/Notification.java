package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import java.util.Objects;

public class Notification extends Application {

    public static final String CHANNEL_ID = "channel1";

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    private static NotificationManager notificationManager;
    @Override
    public void onCreate() {
        super.onCreate();
        //setContentView(R.layout.activity_notification);
        createNotificationChannel("Channel notif", "Channel de notification de budget dépassé", NotificationManager.IMPORTANCE_DEFAULT);
    }

    private void createNotificationChannel(String name, String description, int importance) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);

        }
    }
}