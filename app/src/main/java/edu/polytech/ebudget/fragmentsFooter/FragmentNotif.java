package edu.polytech.ebudget.fragmentsFooter;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.notifications.ListeNotifications;
import edu.polytech.ebudget.notifications.NotificationPage;
import edu.polytech.ebudget.notifications.Notifications;
import edu.polytech.ebudget.notifications.afterNotification;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNotif#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotif extends Fragment {
    private NotificationManager notifManager;
    private NotificationChannel notifChannel;
    private final String chanelId = "i.apps.notifications";
    private final String title = "Titre ma Notif";
    private final String description = "Description notif";
    NotificationCompat.Builder builder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentNotif() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotif newInstance(String param1, String param2) {
        FragmentNotif fragment = new FragmentNotif();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View var_inflater = inflater.inflate(R.layout.activity_notification_page, container, false);

        Button btnAlert = var_inflater.findViewById(R.id.buttonAlert);

        notifManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("test2");
                Intent intent = new Intent(getContext(), afterNotification.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                RemoteViews contentView = new RemoteViews(getActivity().getPackageName(), R.layout.activity_after_notification);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notifChannel = new NotificationChannel(chanelId, title, NotificationManager.IMPORTANCE_HIGH);
                    notifChannel.enableLights(true);
                    notifChannel.setDescription(description);
                    notifChannel.setLightColor(Color.GREEN);
                    notifChannel.enableVibration(false);
                    notifManager.createNotificationChannel(notifChannel);

                    builder = new NotificationCompat.Builder(getContext(), chanelId)
                            .setContent(contentView)
                            .setContentTitle(title)
                            .setSmallIcon(R.drawable.ic_baseline_apps_24)
                            //.setLargeIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_baseline_apps_24))
                            .setContentIntent(pendingIntent);

                }else {
                    builder = new NotificationCompat.Builder(getContext())
                            .setContent(contentView)
                            .setSmallIcon(R.drawable.ic_baseline_apps_24)
                            //.setLargeIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_baseline_apps_24))
                            //.setLargeIcon(((VectorDrawable) ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.ic_baseline_apps_24, null)))
                            .setContentIntent(pendingIntent);
                }


                notifManager.notify(1234, builder.build());
            }
        });


        ImageButton btnNotifList = (ImageButton) var_inflater.findViewById(R.id.button_liste_notifs);
        btnNotifList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("test1");
                Intent intent = new Intent(getContext(), Notifications.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return var_inflater;
    }
}