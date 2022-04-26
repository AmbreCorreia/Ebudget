package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

public class ThresholdDate implements IThreshold{
    private Date date;
    private String category;
    ThresholdDate(Date date, String category){
        this.date = date;
        this.category = category;
    }
    @Override
    public boolean isReached() {
        // compare current Date to threshold
        return false;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public Date firstReached() {
        return date;
    }
}
