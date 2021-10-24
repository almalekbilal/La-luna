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

    int startExpensesIndex = 0;
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
        Date date = new Date();
        date.setDate(1);
        ts.add(dbHandler.getTotalMoneySpent(date));
        ts.add(dbHandler.getTotalBudget(date));

        totalAndSpent.postValue(ts);

        startExpensesIndex = 0;
        checkPrevious(date);
        sendExpenses();


    }

    /**
     * The gets the new expenses needed from the data base handler and send it to the view
     */
    private void sendExpenses(){
        List<Expense> ex = dbHandler.getExpenses(startExpensesIndex, startExpensesIndex +10);

        startExpensesIndex = startExpensesIndex + 10;
        expenses.postValue(ex);

    }

    private void checkPrevious(Date date){
        int totalMoneySpend = dbHandler.getTotalMoneySpent(date);
        if(totalMoneySpend == 0){
            decrementMonth(date);
            if(dbHandler.thereIsCategories(date) && dbHandler.getTotalBudget(date) == 0) {
                dbHandler.setCategoriesPreviousLimits(date);
            }
        }
    }

    private void decrementMonth(Date date){
        int month = date.getMonth();

        if(month == 0){
            int year = date.getYear() -1;
            date.setMonth(11);
            date.setYear(year);
        }else{
            date.setMonth(month - 1);
        }

    }

    public void getMoreExpenses(int position){
        if(position+1 == startExpensesIndex){
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