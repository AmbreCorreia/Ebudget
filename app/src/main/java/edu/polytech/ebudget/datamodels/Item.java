package edu.polytech.ebudget.datamodels;

import java.util.Date;

public class Item {
    public String name;
    public Category category;
    public double price;
    public Date date;
    public int quantity;

    public Item(String name, Category category, double price, Date date){
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = date;
        this.quantity = 1;
    }

    public Item(String name, Category category, double price, Date date, int quantity){
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = date;
        this.quantity = quantity;
    }
}
