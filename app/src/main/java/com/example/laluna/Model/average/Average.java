package com.example.laluna.Model.average;

import com.example.laluna.Model.average.times.TimeObject;
import com.example.laluna.Model.exceptions.IrrelevantDateException;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.Calendar;
import java.util.List;

abstract public class Average {

    protected Calendar start, end;
    private List<TimeObject> timesList;
    private double avarage;
    protected ExpenseRepository expenseRepository;

    public Average(Calendar start, Calendar end, ExpenseRepository expenseRepository) throws IrrelevantDateException {


        this.start = start;
        this.end = end;

        if(start.after(end)){
            throw new IrrelevantDateException();
        }
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
