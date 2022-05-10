package edu.polytech.ebudget.notifications.mvc;

import android.content.Context;

import edu.polytech.ebudget.datamodels.Notification;

public interface INotificationAdapter {
    void onClickNotification(Notification notification, int position);
}
