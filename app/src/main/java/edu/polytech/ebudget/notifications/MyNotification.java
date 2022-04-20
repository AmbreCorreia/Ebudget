package edu.polytech.ebudget.notifications;

import java.util.Date;

public class MyNotification {

    private String categorie;
    //private Date date;
    private String description;

    public MyNotification(String categorie, String description){
        this.categorie = categorie;
        //this.date = date;
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }
/*
    public Date getDate() {
        return date;
    }*/

    public String getDescription() {
        return description;
    }
}
