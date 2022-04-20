package edu.polytech.ebudget.notifications;

import java.util.ArrayList;
import java.util.Date;

public class ListeNotifications extends ArrayList<MyNotification> {
    public ListeNotifications(){
        add(new MyNotification("viande", "budget dépassé"));
        add(new MyNotification("fromage", "budget dépassé"));
        add(new MyNotification("fruits", "budget dépassé"));
        add(new MyNotification("legumes", "budget dépassé"));
        add(new MyNotification("friandises", "budget dépassé"));

    }
}
