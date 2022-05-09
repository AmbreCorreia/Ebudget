package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

import edu.polytech.ebudget.notifications.mvc.ControllerNotification;

public class NotifPhoneFactory extends AbstractNotifFactory{

    public NotifPhone createNotification(ControllerNotification controller, String category, String description, String id, String user, int threshold) {
        return new NotifPhone(controller, description, id, user, new ThresholdValue(threshold, category));
    }

    public NotifPhoneImage createNotification(ControllerNotification controller, String category, String description, String id, String user, int threshold, boolean image) {
        //no matter the boolean value we create a NotifPhoneImage
        return new NotifPhoneImage(controller, description, id, user, new ThresholdValue(threshold, category));
    }

    public NotifCalendar createNotification(ControllerNotification controller, String category, String description, String id, String user, Date threshold) {
        //shouldn't be called
        return null;
    }
}
