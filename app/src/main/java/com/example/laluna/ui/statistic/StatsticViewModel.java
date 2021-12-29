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
import com.example.laluna.Model.avarage.Avarage;
import com.example.laluna.Model.avarage.AvarageFactory;
import com.example.laluna.Model.avarage.times.TimeObject;
import com.example.laluna.Model.repository.DBHandler;
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


    public void init (Calendar start, Calendar end, ExpenseRepository expenseRepository){
        getMonthData(start, end, expenseRepository);

    }

    public LiveData <List<TimeObject>> getCategory() {
        return timeObjectMutableData;
    }

    public void getMonthData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        Avarage avarage = AvarageFactory.getMonthAvarage(start, end, expenseRepository);

       entriesArray = avarage.getTimesList();
       average = avarage.getAvarage();

       averageMutableData.postValue(average);
       timeObjectMutableData.postValue(entriesArray);

    }

    public void getDayData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        Avarage avarage = AvarageFactory.getDayAvarage(start, end, expenseRepository);

        entriesArray = avarage.getTimesList();
        average = avarage.getAvarage();

        averageMutableData.postValue(average);
        timeObjectMutableData.postValue(entriesArray);
    }


    public void getWeekData(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        Avarage avarage = AvarageFactory.getWeekAvarage(start, end, expenseRepository);
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