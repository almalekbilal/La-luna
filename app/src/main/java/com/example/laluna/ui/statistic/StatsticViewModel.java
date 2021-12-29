package com.example.laluna.ui.statistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.laluna.Model.average.Average;
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


    public void init (Calendar start, Calendar end, ExpenseRepository expenseRepository){
        getMonthData(start, end, expenseRepository);

    }

    public LiveData <List<TimeObject>> getCategory() {
        return timeObjectMutableData;
    }

    public void getMonthData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        Average avarage = AverageFactory.getMonthAvarage(start, end, expenseRepository);

       entriesArray = avarage.getTimesList();
       average = avarage.getAvarage();

       averageMutableData.postValue(average);
       timeObjectMutableData.postValue(entriesArray);

    }

    public void getDayData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        Average avarage = AverageFactory.getDayAvarage(start, end, expenseRepository);

        entriesArray = avarage.getTimesList();
        average = avarage.getAvarage();

        averageMutableData.postValue(average);
        timeObjectMutableData.postValue(entriesArray);
    }


    public void getWeekData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        Average avarage = AverageFactory.getWeekAvarage(start, end, expenseRepository);
        entriesArray = avarage.getTimesList();
        average = avarage.getAvarage();

        averageMutableData.postValue(average);
        timeObjectMutableData.postValue(entriesArray);
    }

    public LiveData <List<TimeObject>> getTimeData(){
        return timeObjectMutableData;
    }

    public LiveData <Double> getAverageData(){
        return averageMutableData;
    }


}