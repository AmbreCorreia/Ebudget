package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

public interface INotification {

    public IThreshold getThreshold();
    public String getDescription();
    public void addToDatabase();
}
