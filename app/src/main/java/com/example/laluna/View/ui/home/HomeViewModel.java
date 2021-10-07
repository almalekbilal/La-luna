package com.example.laluna.View.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.DBHandler;
import com.example.laluna.Model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends ViewModel {

    int start = 0;
    private MutableLiveData<List<Integer>> totalAndSpent = new MutableLiveData<>();
    private MutableLiveData<List<Expense>> expenses = new MutableLiveData<>();
    private DBHandler dbHandler;

    public HomeViewModel() {

    }

    public void init(Context context){
        dbHandler = new DBHandler(context);

        List<Integer> ts = new ArrayList<>();
        ts.add(dbHandler.getTotalMoneySpent(new Date()));
        ts.add(dbHandler.getTotalBudget(new Date()));

        totalAndSpent.postValue(ts);


        sendExpenses();
    }

    private void sendExpenses(){
        List<Expense> ex = dbHandler.getExpenses(start, start+10);

        start = start + 10;
        expenses.postValue(ex);

    }

    public LiveData<List<Integer>> getTotalAndSpent(){ return totalAndSpent; }

    public LiveData<List<Expense>> getExpenses(){ return expenses; }


}