package com.example.laluna.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.DBHandler;
import com.example.laluna.Model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ViewModel class that is responsible for the communication with the data base handler and the logic behind the scene
 *
 *
 *   @auther (Deaa Khankan)
 *   @auther (Ali Al Khaled)
 */
public class HomeViewModel extends ViewModel {

    int start = 0;
    private MutableLiveData<List<Integer>> totalAndSpent = new MutableLiveData<>();
    private MutableLiveData<List<Expense>> expenses = new MutableLiveData<>();
    private DBHandler dbHandler;

    public HomeViewModel() {

    }

    /**
     * The method works when the class is running for the first time
     * It gets the total money spend and the budget this month from data base handler and sends them to the view
     * It gets also the latest expenses and sends them to the view
     * @param context the android information that is needen for the database
     */
    public void init(Context context){
        dbHandler = new DBHandler(context);

        List<Integer> ts = new ArrayList<>();
        ts.add(dbHandler.getTotalMoneySpent(new Date()));
        ts.add(dbHandler.getTotalBudget(new Date()));

        totalAndSpent.postValue(ts);

        start = 0;
        sendExpenses();
    }

    /**
     * The gets the new expenses needed from the data base handler and send it to the view
     */
    private void sendExpenses(){
        List<Expense> ex = dbHandler.getExpenses(start, start+10);

        start = start + 10;
        expenses.postValue(ex);

    }

    public void getMoreExpenses(int position){
        if(position+1 == start){
            sendExpenses();
        }
    }

    /**
     * The method returns the total money spend and the budget as a LiveData object
     * Its responsible for the communication with the view
     */
    public LiveData<List<Integer>> getTotalAndSpent(){ return totalAndSpent; }

    /**
     * The method returns a list of expenses as a LiveData object
     * Its responsible for the communication with the view
     */
    public LiveData<List<Expense>> getExpenses(){ return expenses; }


}