package edu.polytech.ebudget.notifications;

import edu.polytech.ebudget.datamodels.Notification;

public interface IViewClick {
    void onClickNotification(NotificationModel notification, int position);
}
