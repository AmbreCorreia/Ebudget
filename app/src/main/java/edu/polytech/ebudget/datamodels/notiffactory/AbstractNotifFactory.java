package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

public abstract class AbstractNotifFactory {
    abstract IThreshold createThreshold(Date date, String category);
    abstract IThreshold createThreshold(int value, String category);
    abstract INotification createNotification(String description, String user, IThreshold threshold);
}
