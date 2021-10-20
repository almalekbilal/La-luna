package com.example.laluna.ui.analysis.categoryExpensesActivity;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.DBHandler;
import com.example.laluna.Model.Expense;

import java.util.ArrayList;

import java.util.List;

public class CategoryExpensesViewModel extends ViewModel {

    private DBHandler dbHandler;
    private MutableLiveData<List<Expense>> categoryExpenses;





    public void init(Context context){
        dbHandler = new DBHandler(context);

        categoryExpenses = new MutableLiveData<>();

    }




    public void updateCategoryExpenses(int categoryId,int year,int month){

        List<Expense> expenses = new ArrayList<>();

        List<Expense> categoryEx = dbHandler.getCategoryExpense(categoryId);



        for(Expense expense: categoryEx){
            if(expense.get_date().getYear() == year &&
                    expense.get_date().getMonth() == month){
                expenses.add(expense);
            }
        }
        categoryExpenses.postValue(expenses);
    }




    public LiveData<List<Expense>> getCategoryExpenses() {
        return categoryExpenses;
    }
}
