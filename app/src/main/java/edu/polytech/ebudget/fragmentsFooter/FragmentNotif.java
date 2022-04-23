package edu.polytech.ebudget.fragmentsFooter;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import static edu.polytech.ebudget.notifications.ApplicationDemo.CHANNEL_ID;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.notifications.ApplicationDemo;
import edu.polytech.ebudget.notifications.Notifications;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNotif#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotif extends Fragment {
    private int notificationId = 0;
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

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("test2");
                sendNotificationOnChannel("Attention!", "Vous avez dépassé votre budget...", CHANNEL_ID, NotificationCompat.PRIORITY_HIGH);

            }

            private void sendNotificationOnChannel(String title, String message, String channelId, int priority) {
                NotificationCompat.Builder notification = new NotificationCompat.Builder(getActivity().getApplicationContext(), channelId)
                        .setSmallIcon(R.drawable.ic_baseline_apps_24)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(priority);
                ApplicationDemo.getNotificationManager().notify(++notificationId, notification.build());
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