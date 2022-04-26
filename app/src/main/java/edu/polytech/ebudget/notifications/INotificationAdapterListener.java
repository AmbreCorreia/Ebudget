package edu.polytech.ebudget.notifications;

import edu.polytech.ebudget.datamodels.Notification;

public interface INotificationAdapterListener {
    void onClickNotification(Notification notification, int position);

}