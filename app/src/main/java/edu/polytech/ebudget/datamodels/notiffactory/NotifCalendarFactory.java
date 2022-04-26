package edu.polytech.ebudget.datamodels.notiffactory;

import android.util.Log;

import java.util.Date;

public class NotifCalendarFactory extends AbstractNotifFactory {

    @Override
    IThreshold createThreshold(Date date, String category) {
        return new ThresholdDate(date, category);
    }

    @Override
    IThreshold createThreshold(int value, String category) {
        Log.i("Factory","NotifCalendarFactory shouldn't receive a value but a Date");
        return null;
    }

    @Override
    INotification createNotification(String description, String user, IThreshold threshold) {
        return new NotifCalendar(description, user, threshold);
    }
}
