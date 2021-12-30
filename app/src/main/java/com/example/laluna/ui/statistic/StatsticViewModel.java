package com.example.laluna.ui.statistic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;


import com.example.laluna.Model.Category;
import com.example.laluna.Model.average.Average;
import com.example.laluna.Model.average.AverageFactory;
import com.example.laluna.Model.average.times.TimeObject;

import com.example.laluna.Model.repository.ExpenseRepository;
import com.example.laluna.R;
import com.example.laluna.ui.analysis.AnalysisViewModel;
import com.example.laluna.ui.analysis.CategoryWithMoney;
import com.example.laluna.ui.analysis.GridViewAdapter;
import com.example.laluna.ui.analysis.categoryExpensesActivity.CategoryExpensesActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatsticViewModel extends ViewModel {


    List<TimeObject> entriesArray;
    private double average;


    private MutableLiveData <List<TimeObject>> timeObjectMutableData= new MutableLiveData<>();
    private MutableLiveData <Double> averageMutableData = new MutableLiveData<>();

    public StatsticViewModel() {

    }

    public void init (Calendar start, Calendar end, ExpenseRepository expenseRepository){
        getDayData(start, end, expenseRepository);

    }

    public LiveData <List<TimeObject>> getCategory() {
        return timeObjectMutableData;
    }

    public void getMonthData(Calendar start, Calendar end, ExpenseRepository expenseRepository){

        Average avarage = AverageFactory.getMonthAvarage(start, end, expenseRepository);

       entriesArray = avarage.getTimesList();
       average = avarage.getAvarage();
        System.out.println(entriesArray.size()+"im here dont worry");
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