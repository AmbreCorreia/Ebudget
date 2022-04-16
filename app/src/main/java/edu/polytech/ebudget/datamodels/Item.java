package edu.polytech.ebudget.datamodels;

import java.util.Date;

public class Item {
    public String name;
    public Category category;
    public int price;
    public Date date;
    public int quantity;
    public String user;

    public Item(String name, Category category, int price, Date date, String user){
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = date;
        this.quantity = 1;
        this.user = user;
    }

    public Item(String name, Category category, int price, Date date, int quantity, String user){
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = date;
        this.quantity = quantity;
        this.user = user;
    }
}
