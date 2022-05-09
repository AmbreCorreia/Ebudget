package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

import edu.polytech.ebudget.notifications.mvc.ControllerNotification;

public abstract class AbstractNotifFactory {
    public abstract NotifPhone createNotification(ControllerNotification controller, String category, String description, String id, String user, int threshold);
    public abstract NotifPhoneImage createNotification(ControllerNotification controller, String category,String description,String id, String user, int threshold, boolean image);
    public abstract NotifCalendar createNotification(ControllerNotification controller, String category,String description,String id, String user, Date threshold);
}
