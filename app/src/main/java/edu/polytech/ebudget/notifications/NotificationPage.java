package edu.polytech.ebudget.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import edu.polytech.ebudget.R;


public class NotificationPage extends AppCompatActivity {
    private NotificationManager notifManager;
    private NotificationChannel notifChannel;
    private final String chanelId = "i.apps.notifications";
    private final String description = "Description notif";
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        Button btnAlert = findViewById(R.id.buttonAlert);

        notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationPage.this, afterNotification.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationPage.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.activity_after_notification);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notifChannel = new NotificationChannel(chanelId, description, NotificationManager.IMPORTANCE_HIGH);
                    notifChannel.enableLights(true);
                    notifChannel.setLightColor(Color.GREEN);
                    notifChannel.enableVibration(false);
                    notifManager.createNotificationChannel(notifChannel);

                    builder = new NotificationCompat.Builder(getApplicationContext(), chanelId)
                            .setContent(contentView)
                            .setSmallIcon(R.drawable.ic_baseline_apps_24)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_apps_24))
                            .setTimeoutAfter(20000)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setContentIntent(pendingIntent);

                }else {
                    builder = new NotificationCompat.Builder(getApplicationContext())
                            .setContent(contentView)
                            .setSmallIcon(R.drawable.ic_baseline_apps_24)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_apps_24))
                            .setContentIntent(pendingIntent);
                }

                notifManager.notify(1234, builder.build());
            }
        });

        ImageButton btnNotifList = (ImageButton) findViewById(R.id.button_liste_notifs);
        btnNotifList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationPage.this, ListeNotifications.class);
                startActivity(intent);
            }
        });
    }
}