package com.example.laluna.Model.avarage;

import com.example.laluna.Model.avarage.times.TimeObject;

import java.util.Calendar;
import java.util.List;

abstract class Avarage {

    protected Calendar start, end;
    private List<TimeObject> timesList;
    private double avarage;

    public Avarage(Calendar start, Calendar end){
        this.start = start;
        this.end = end;
        timesList = makeTimesList();
        calculateAvarage();
    }

    public List<TimeObject> getTimesList() {
        return timesList;
    }

    public void setTimesList(List<TimeObject> timesList) {
        this.timesList = timesList;
    }

    public double getAvarage() {
        return avarage;
    }

    public void setAvarage(double avarage) {
        this.avarage = avarage;
    }

    abstract List<TimeObject> makeTimesList();

    private void calculateAvarage(){
        double sum = 0;
        for(TimeObject time : timesList){
            sum += time.getValue();
        }
        avarage = sum / timesList.size();
    }
}
