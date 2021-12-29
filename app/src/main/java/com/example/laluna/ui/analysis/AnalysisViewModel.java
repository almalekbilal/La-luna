package com.example.laluna.ui.analysis;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.CategoryWithExpenses;
import com.example.laluna.Model.DateConverter;
import com.example.laluna.Model.repository.CategoryRepository;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * ViewModel class that is responsible for the communication with the data base handler and the logic behind the scene
 *
 *
 *   @auther (Bilal Al Malek)
 *   @auther (Ali Malla)
 */
public class AnalysisViewModel extends ViewModel {

    private MutableLiveData<List<Integer>> totalAndSpent = new MutableLiveData<>();
    private MutableLiveData<List<CategoryWithExpenses>> categoriesLiveData;
    private MutableLiveData<Date> viewMonthDate = new MutableLiveData<>();

    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;

    public AnalysisViewModel() {

    }

    /**
     * The method works when the class is running for the first time
     * It update the date and sends it to the view
     * It gets the total money spend and the budget this month from database handler and sends them to the view
     * It gets the categories for this month from database and sends it to the view
     * @param context the android information that is needen for the database
     */
    public void init(Context context){

        categoryRepository = CategoryRepository.getInstance(context);
        expenseRepository = ExpenseRepository.getInstance(context);

        Date date = new Date();
        date.setDate(1);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        viewMonthDate.postValue(date);
        viewMonthDate.setValue(date);

        categoriesLiveData = new MutableLiveData<>();

        updateView();
    }



    /**
     * The method gets the categories of a specific month and sends them to the view
     * It gets the limit of each category and sends it to the view
     * It gets the total money spend of each category and sends it to the view
     */
    private void updateCategories(){
        List<CategoryWithExpenses> cat = categoryRepository.getCategoriesWithExpenses(viewMonthDate.getValue());


        categoriesLiveData.postValue(cat);

    }




    /**
     * The methods is called whenever the user clicks on the left arrow
     * The method change the date to the previous month and updates the view
     */
    public void leftArrowClick(){
        viewMonthDate.setValue(DateConverter.decrementMonth(viewMonthDate.getValue()));

        if(categoryRepository.thereIsCategories(viewMonthDate.getValue())){

            updateView();
        }else {

            viewMonthDate.setValue(DateConverter.incrementMonth(viewMonthDate.getValue()));
        }
    }

    /**
     * The methods is called whenever the user clicks on the right arrow
     * The method change the date to the previous month and updates the view
     */
    public void rightArrowClick(){

        viewMonthDate.setValue(DateConverter.incrementMonth(viewMonthDate.getValue()));

        if(categoryRepository.thereIsCategories(viewMonthDate.getValue())){
            updateView();
        }else {
            viewMonthDate.setValue(DateConverter.decrementMonth(viewMonthDate.getValue()));
        }
    }



    /**
     * The method takes the information from database and sends them to view
     * It sends the date to the view
     * It gets the total money spend and the budget this month from database handler and sends them to the view
     * It gets the categories for this month from database and sends it to the view
     */
    private void updateView(){
        List<Integer> ts = new ArrayList<>();
        ts.add(expenseRepository.getTotalMoneySpend(viewMonthDate.getValue()));
        ts.add(categoryRepository.getTotalBudget(viewMonthDate.getValue()));
        totalAndSpent.postValue(ts);
        updateCategories();
        Date date = viewMonthDate.getValue();
        viewMonthDate.postValue(date);
        viewMonthDate.setValue(date);
    }


    /**
     * The method returns the categories as a LiveData object
     * Its responsible for the communication with the view
     */
    public LiveData<List<CategoryWithExpenses>> getCategories(){ return categoriesLiveData; }

    /**
     * The method returns the total money spend and the budget as a LiveData object
     * Its responsible for the communication with the view
     */
    public LiveData<List<Integer>> getTotalAndSpent(){ return totalAndSpent; }

    /**
     * The method returns the the date as a LiveData object
     * Its responsible for the communication with the view
     */
    public LiveData<Date> getDate(){return viewMonthDate;}


}