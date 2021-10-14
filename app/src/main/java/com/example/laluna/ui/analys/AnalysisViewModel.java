package com.example.laluna.ui.analys;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;

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
    private MutableLiveData<List<CategoryWithMoney>> categoriesLiveData;
    private DBHandler dbHandler;
    private MutableLiveData<Date> viewMonthDate = new MutableLiveData<>();

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
        dbHandler = new DBHandler(context);

        viewMonthDate.postValue(new Date());
        viewMonthDate.setValue(new Date());

        categoriesLiveData = new MutableLiveData<>();
        updateView();
    }

    /**
     * The method gets the categories of a specific month and sends them to the view
     * It gets the limit of each category and sends it to the view
     * It gets the total money spend of each category and sends it to the view
     */
    private void updateCategories(){
        List<CategoryWithMoney> cat = new ArrayList<>();

        List<Category> categories = dbHandler.getCategories(viewMonthDate.getValue());

        for(Category category : categories){
            cat.add(new CategoryWithMoney(category, getLimit(category,viewMonthDate.getValue()) , dbHandler.getTotalSpentByCategory(viewMonthDate.getValue(),category)));
        }

        categoriesLiveData.postValue(cat);

    }

    /**
     * The method takes a category and a date that represent a month and returns the limits of the category in that month
     * @param category
     * @param date
     * @return
     */
    private int getLimit(Category category, Date date){
        Date dateNow = new Date();

        if(dateNow.getYear() == date.getYear() && dateNow.getMonth() == date.getMonth()){
            return category.get_limit();
        }else{
            return dbHandler.getCategoryLimit(date,category);
        }
    }

    /**
     * The methods is called whenever the user clicks on the left arrow
     * The method change the date to the previous month and updates the view
     */
    public void leftArrowClick(){
        decrementMonth();

        if(dbHandler.getCategories(viewMonthDate.getValue()).size() == 0){
            incrementMonth();
        }else {

            updateView();
        }
    }

    /**
     * The methods is called whenever the user clicks on the right arrow
     * The method change the date to the previous month and updates the view
     */
    public void rightArrowClick(){

        incrementMonth();

        if(dbHandler.getCategories(viewMonthDate.getValue()).size() == 0){
            decrementMonth();
        }else {
            updateView();
        }
    }

    /**
     * The method change the date to the next month
     */

    private void incrementMonth(){
        int month = viewMonthDate.getValue().getMonth();
        Date date = new Date();
        if(month == 12){
            int year = viewMonthDate.getValue().getYear() +1;
            viewMonthDate.getValue().setMonth(0);
            viewMonthDate.getValue().setYear(year);
        }else{
            viewMonthDate.getValue().setMonth(month + 1);
        }
    }


    /**
     * The method change the date to the previous month
     */
    private void decrementMonth(){
        int month = viewMonthDate.getValue().getMonth();

        if(month == 0){
            int year = viewMonthDate.getValue().getYear() -1;
            viewMonthDate.getValue().setMonth(11);
            viewMonthDate.getValue().setYear(year);
        }else{
            viewMonthDate.getValue().setMonth(month - 1);
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
        ts.add(dbHandler.getTotalMoneySpent(viewMonthDate.getValue()));
        ts.add(dbHandler.getTotalBudget(viewMonthDate.getValue()));
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
    public LiveData<List<CategoryWithMoney>> getCategories(){ return categoriesLiveData; }

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