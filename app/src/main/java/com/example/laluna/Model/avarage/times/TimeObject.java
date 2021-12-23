package com.example.laluna.Model.avarage.times;

import java.util.Calendar;

public class TimeObject {
    Calendar date;
    private double value;
    public TimeObject(Calendar date, double value){
        this.date = date;
        this.value = value;
    }

    public double getValue(){
        return value;
    }

}
