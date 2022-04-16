package edu.polytech.ebudget.datamodels;

public class Category {
    public String name;
    public int budget;
    public String user;

    public Category(String name, int budget, String user){
        this.name = name;
        this.budget = budget;
        this.user = user;
    }
}
