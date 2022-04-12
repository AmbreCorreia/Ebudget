package edu.polytech.ebudget;

import static edu.polytech.ebudget.Notification.CHANNEL_ID;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;

public class NotificationPage extends AppCompatActivity {
    private int notificationId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViewById(R.id.switchNotifs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotificationChannel("Attention !", "Vous avez dépassé votre budget !", CHANNEL_ID, NotificationCompat.PRIORITY_DEFAULT);
            }
        });
    }

    private void sendNotificationChannel(String title, String message, String channelId, int priority){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_baseline_apps_24)
                .setContentTitle(title)
                .setContentText("id=" + notificationId + "-" + message)
                .setPriority(priority);
        Notification.getNotificationManager().notify(++notificationId, notification.build());

    }
}
