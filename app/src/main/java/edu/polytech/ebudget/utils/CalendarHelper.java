package edu.polytech.ebudget.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import edu.polytech.ebudget.FragmentChooseCalendar;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Preference;

public class CalendarHelper {

    public CalendarHelper() {
    }

    private static void addOneEvent(Activity activity, Context context, String msg, ArrayList<Preference> pref) {
        String[] projection = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME};
        String calendarEmailAddress = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Log.d("ISEMPTYBEFORE:", String.valueOf(pref.isEmpty()));
        if (!pref.isEmpty()) {
            long calendarID = pref.get(0).calendarID;
            Log.d("IFLOG:", "Je ne suis pas vide");

            try {
                ContentResolver cr = activity.getContentResolver();
                Cursor cursor = cr.query(CalendarContract.Calendars.CONTENT_URI, projection,
                        CalendarContract.Calendars.ACCOUNT_NAME + "=? and (" +
                                CalendarContract.Calendars.NAME + "=? or " +
                                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + "=?)",
                        new String[]{calendarEmailAddress, calendarEmailAddress,
                                calendarEmailAddress}, null);

                if (cursor.moveToFirst()) {
                    if (cursor.getString(1).equals(calendarEmailAddress)) {
                        calendarID = cursor.getInt(0);
                    }
                }

                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, android.icu.util.Calendar.getInstance().getTimeInMillis());
                values.put(CalendarContract.Events.DTEND, android.icu.util.Calendar.getInstance().getTimeInMillis() + 60000);
                values.put(CalendarContract.Events.TITLE, "Ebudget");
                values.put(CalendarContract.Events.DESCRIPTION, msg);
                values.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                Toast.makeText(activity.getApplicationContext(), "Event pushed to Calendar",
                        Toast.LENGTH_SHORT).show();

            } catch (SecurityException e) {
                Log.d("Calendar", "Failed: Permission denied");
            } catch (Exception exception) {
                Log.d("Calendar", "Failed: Unknown reason");
            }
        } else {
            Log.d("IFLOG:", "Je suis vide");
            Bundle bundle = new Bundle();
            bundle.putString("param1", msg);
            FragmentChooseCalendar frag = new FragmentChooseCalendar();
            frag.setArguments(bundle);
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, frag);
            fragmentTransaction.commit();
        }
    }

    public static void addCalendarEvent(Activity activity, Context context, String msg) {

        ArrayList<Preference> pref = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(FirebasePaths.preferences)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .whereNotEqualTo("calendarID", -1)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Preference preference = document.toObject(Preference.class);
                        Log.d("chaque pref:", String.valueOf(preference));
                        pref.add(preference);
                        Log.d("array:", pref.toString());
                    }
                    CalendarHelper.addOneEvent(activity, context, msg, pref);
                });
    }
}
