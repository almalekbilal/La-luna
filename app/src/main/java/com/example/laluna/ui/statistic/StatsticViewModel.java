package com.example.laluna.ui.statistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.laluna.Model.average.AverageFactory;
import com.example.laluna.Model.average.times.TimeObject;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.Calendar;
import java.util.List;

public class StatsticViewModel extends ViewModel {


    List<TimeObject> entriesArray;
    private double average;


    private MutableLiveData <List<TimeObject>> timeObjectMutableData= new MutableLiveData<>();
    private MutableLiveData <Double> averageMutableData = new MutableLiveData<>();


    public StatsticViewModel(Calendar start, Calendar end, ExpenseRepository expenseRepository ) {
        getMonthData(start, end, expenseRepository);
        timeObjectMutableData.postValue(entriesArray);
        averageMutableData.postValue(average);

    }

    public LiveData <List<TimeObject>> getCategory() {
        return timeObjectMutableData;
    }

    public void getMonthData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
       entriesArray = AverageFactory.getMonthAvarage(start, end, expenseRepository).getTimesList();
       average = AverageFactory.getMonthAvarage(start, end, expenseRepository).getAvarage();

    }

    public void getDayData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        entriesArray = AverageFactory.getDayAvarage(start, end, expenseRepository).getTimesList();
        average = AverageFactory.getDayAvarage(start, end, expenseRepository).getAvarage();
    }


    public void getWeekData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        entriesArray = AverageFactory.getWeekAvarage(start, end, expenseRepository).getTimesList();
        average = AverageFactory.getWeekAvarage(start, end, expenseRepository).getAvarage();
    }




}