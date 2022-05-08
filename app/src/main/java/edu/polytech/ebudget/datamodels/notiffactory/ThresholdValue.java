package edu.polytech.ebudget.datamodels.notiffactory;

import java.io.Serializable;
import java.util.Date;

public class ThresholdValue implements IThreshold, Serializable {
    private int value;
    private String category;

    ThresholdValue(int val, String category){
        this.value = val;
        this.category = category;
    }

    public boolean isReached() {
        //currently checked externaly
        return false;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public int getValue(){
        return value;
    }
}
