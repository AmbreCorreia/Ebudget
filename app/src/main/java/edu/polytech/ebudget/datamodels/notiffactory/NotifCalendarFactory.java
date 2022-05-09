package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

import edu.polytech.ebudget.notifications.mvc.ControllerNotification;

public class NotifCalendarFactory extends AbstractNotifFactory {

    @Override
    public NotifPhone createNotification(ControllerNotification controller, String category, String description, String id, String user, int threshold) {
        //shouldn't be called
        return null;
    }

    @Override
    public NotifPhoneImage createNotification(ControllerNotification controller, String category, String description, String id, String user, int threshold, boolean image) {
        //shouldn't be called
        return null;
    }

    @Override
    public NotifCalendar createNotification(ControllerNotification controller, String category, String description, String id, String user, Date threshold) {
        return null;
        //return new NotifCalendar(controller, description, id, user, new ThresholdDate(threshold, category));
    }
}
