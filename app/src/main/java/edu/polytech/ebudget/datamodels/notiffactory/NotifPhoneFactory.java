package edu.polytech.ebudget.datamodels.notiffactory;

import android.util.Log;

import java.util.Date;

public class NotifPhoneFactory extends AbstractNotifFactory{


    @Override
    IThreshold createThreshold(Date date, String category) {
        Log.i("Factory","NotifPhoneFactory shouldn't receive a Date");
        return null;
    }

    @Override
    IThreshold createThreshold(int value, String category) {
        return null;
    }

    @Override
    INotification createNotification(String description, String user, IThreshold threshold) {
        return new NotifPhone(description,user,threshold);
    }
}
