package com.example.laluna.Model.avarage;

import com.example.laluna.Model.Expense;
import com.example.laluna.Model.avarage.times.TimeObject;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.Calendar;
import java.util.List;

abstract public class Avarage {

    protected Calendar start, end;
    private List<TimeObject> timesList;
    private double avarage;
    protected ExpenseRepository expenseRepository;

    public Avarage(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        this.start = start;
        this.end = end;
        this.expenseRepository = expenseRepository;
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


    abstract List<TimeObject> makeTimesList();

    private void calculateAvarage(){
        double sum = 0;
        for(TimeObject time : timesList){
            sum += time.getValue();
        }
        avarage = sum / timesList.size();
    }
}
