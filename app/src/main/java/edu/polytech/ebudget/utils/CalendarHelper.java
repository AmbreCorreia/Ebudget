package edu.polytech.ebudget.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CalendarHelper {

    public CalendarHelper(){}

    public static void addCalendarEvent(Activity activity, Context context, String msg){
        String calendarEmailAddress= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Log.d("agenda email", calendarEmailAddress);

        long calendarID = 0;

        String[] projection = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME};

        ContentResolver cr = activity.getContentResolver();

        Cursor cursor = cr.query(CalendarContract.Calendars.CONTENT_URI, projection,
                CalendarContract.Calendars.ACCOUNT_NAME + "=? and (" +
                        CalendarContract.Calendars.NAME + "=? or " +
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + "=?)",
                new String[]{calendarEmailAddress, calendarEmailAddress,
                        calendarEmailAddress}, null);

        if (cursor.moveToFirst()) {
            if (cursor.getString(1).equals(calendarEmailAddress)){
                calendarID = cursor.getInt(0);
            }
        }

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, android.icu.util.Calendar.getInstance().getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, android.icu.util.Calendar.getInstance().getTimeInMillis()+1);
        values.put(CalendarContract.Events.TITLE, "Ebudget");
        values.put(CalendarContract.Events.DESCRIPTION, msg);
        values.put(CalendarContract.Events.CALENDAR_ID, calendarID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

        Toast.makeText(context, "Event pushed to Calendar",
                Toast.LENGTH_SHORT).show();
    }
}
