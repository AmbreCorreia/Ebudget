package edu.polytech.ebudget.fragmentsFooter;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import static edu.polytech.ebudget.notifications.ApplicationDemo.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.Notification;
import edu.polytech.ebudget.notifications.ApplicationDemo;
import edu.polytech.ebudget.notifications.ClasseQuiAppelleTout;
import edu.polytech.ebudget.notifications.Notifications;
import edu.polytech.ebudget.utils.CalendarHelper;

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
        Spinner categorySpinner = (Spinner) var_inflater.findViewById(R.id.spinner_category_notif);

        FirebaseFirestore.getInstance().collection("categories")
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList documents = new ArrayList();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Category cat = document.toObject(Category.class);
                            documents.add(cat.name);
                        }

                        ArrayAdapter array = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, documents);
                        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        categorySpinner.setAdapter(array);
                    }
                });

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotificationOnChannel("Attention!", "Vous avez dépassé votre budget...", CHANNEL_ID, NotificationCompat.PRIORITY_DEFAULT);
                String category = categorySpinner.getSelectedItem().toString();
                String user = FirebaseAuth.getInstance().getUid();
                String description = "Vous avez dépassé votre budget de " + category;
                System.out.println("notifId: " + notificationId);
                String id = String.valueOf(notificationId);
                System.out.println("id: " + id);

                new Notification(category, description, user, id).addToDatabase();
            }

            private void sendNotificationOnChannel(String title, String message, String channelId, int priority) {

                NotificationCompat.Builder notification = new NotificationCompat.Builder(getActivity().getApplicationContext(), channelId)
                        .setSmallIcon(R.drawable.ic_baseline_apps_24)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(priority)
                        .setTimeoutAfter(2500);
                ApplicationDemo.getNotificationManager().notify(++notificationId, notification.build());
            }
        });


        ImageButton btnNotifList = (ImageButton) var_inflater.findViewById(R.id.button_liste_notifs);
        btnNotifList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Notifications.class);
                startActivity(intent);

                //ClasseQuiAppelleTout.onViewCreated(R.layout.activity_notifications);
                //((ClasseQuiAppelleTout)getApplication()).onViewCreated(  findViewById(R.id.activity_notifications) );
            }
        });

       /*var_inflater.findViewById(R.id.AddCAlendarEvent).setOnClickListener(click -> {
           Intent insertCalendarIntent = new Intent(Intent.ACTION_INSERT)
                   .setData(CalendarContract.Events.CONTENT_URI)
                   .putExtra(CalendarContract.Events.TITLE, "budget dépassé")
                   .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                   .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Calendar.getInstance().getTimeInMillis()) // Only date part is considered when ALL_DAY is true; Same as DTSTART
                   .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,Calendar.getInstance().getTimeInMillis()+1) // Only date part is considered when ALL_DAY is true
                   .putExtra(CalendarContract.Events.DESCRIPTION, "budget dépassé"); // Description

           startActivity(insertCalendarIntent);

       });*/

        var_inflater.findViewById(R.id.AddCAlendarEvent).setOnClickListener(click -> {
            CalendarHelper.addCalendarEvent(getActivity(), getContext(), "Budget dépassé");
        });


        // Inflate the layout for this fragment
        return var_inflater;
    }
}