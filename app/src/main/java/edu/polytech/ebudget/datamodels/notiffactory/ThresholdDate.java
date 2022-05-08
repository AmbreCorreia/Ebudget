package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

public class ThresholdDate implements IThreshold{
    private Date date;
    private String category;

    ThresholdDate(Date date, String category){
        this.date = date;
        this.category = category;
    }

    public boolean isReached(Date currentdate) {
        return date.compareTo(currentdate)<=0;
    }

    @Override
    public String getCategory() {
        return category;
    }
}
