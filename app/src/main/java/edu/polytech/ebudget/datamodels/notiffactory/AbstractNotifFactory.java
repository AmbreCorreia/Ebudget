package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

import edu.polytech.ebudget.notifications.mvc.NotificationController;

public abstract class AbstractNotifFactory {
    public abstract NotifPhone createNotification(NotificationController controller, String category, String description, String id, String user, int threshold);
    public abstract NotifPhoneImage createNotification(NotificationController controller, String category,String description,String id, String user, int threshold, boolean image);
    public abstract NotifCalendar createNotification(NotificationController controller, String category,String description,String id, String user, Date threshold);
}
