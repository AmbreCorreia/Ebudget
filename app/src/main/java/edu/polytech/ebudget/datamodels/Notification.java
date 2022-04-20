package edu.polytech.ebudget.datamodels;

import java.util.Date;

public class Notification {

    private String categorie;
    //private Date date;
    private String description;

    public Notification(String categorie, String description){
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
