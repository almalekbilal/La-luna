package com.example.laluna.ui.analysis.categoryExpensesActivity;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.DBHandler;
import com.example.laluna.Model.Expense;

import java.util.ArrayList;

import java.util.List;

/**
 * CategoryExpensesViewModel class that is responsible for the communication with the
 * data base handler and the logic behind the scene
 * @author Ali Malla
 */
public class CategoryExpensesViewModel extends ViewModel {

    private DBHandler dbHandler;
    private MutableLiveData<List<Expense>> categoryExpenses;


    /**
     * This method initializes the class's attributes
     * @param context the view context that will work with this class
     */
    public void init(Context context){
        dbHandler = new DBHandler(context);

        categoryExpenses = new MutableLiveData<>();
    }


    /**
     *  The method gets the expenses of a specific category and sends them to the view
     * @param categoryId  id of the specific category
     * @param year Expenses year
     * @param month Expenses month
     */
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


    /**
     * The method returns the specific category expenses as a LiveData object
     * Its responsible for the communication with the view
     * @return category expenses as a liveData object
     */
    public LiveData<List<Expense>> getCategoryExpenses() {
        return categoryExpenses;
    }

}
