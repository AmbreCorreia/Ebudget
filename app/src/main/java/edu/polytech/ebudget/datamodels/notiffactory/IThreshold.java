package edu.polytech.ebudget.datamodels.notiffactory;

import java.util.Date;

public interface IThreshold {

    boolean isReached();
    String getCategory();
    Date firstReached();
}
