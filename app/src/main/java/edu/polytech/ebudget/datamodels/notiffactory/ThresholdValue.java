package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

public class ThresholdValue implements IThreshold{
    private int value;
    private String category;
    private Date date;

    ThresholdValue(int val, String category){
        this.value = val;
    }

    @Override
    public boolean isReached() {
        //check if category's value is higher than threshold
        //update date if date is null
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
