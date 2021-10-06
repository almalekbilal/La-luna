package com.example.laluna.View.ui.analys;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;
import com.example.laluna.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalysisViewModel extends ViewModel {

    private MutableLiveData<List<Integer>> totalAndSpent = new MutableLiveData<>();
    private MutableLiveData<List<CategoryWithMoney>> categoriesLiveData;
    private DBHandler dbHandler;
    private MutableLiveData<Date> viewMonthDate = new MutableLiveData<>();

    public AnalysisViewModel() {





    }

    public void init(Context context){
        dbHandler = new DBHandler(context);

        viewMonthDate.postValue(new Date());
        viewMonthDate.setValue(new Date());

        categoriesLiveData = new MutableLiveData<>();
        updateView();
    }

    private void updateCategories(){
        List<CategoryWithMoney> cat = new ArrayList<>();

        List<Category> categories = dbHandler.getCategories(viewMonthDate.getValue());

        for(Category category : categories){
            cat.add(new CategoryWithMoney(category, getLimit(category,viewMonthDate.getValue()) , dbHandler.getTotalSpentByCategory(viewMonthDate.getValue(),category)));
        }

        categoriesLiveData.postValue(cat);

    }

    private int getLimit(Category category, Date date){
        Date dateNow = new Date();

        if(dateNow.getYear() == date.getYear() && dateNow.getMonth() == date.getMonth()){
            return category.get_limit();
        }else{
            return dbHandler.getCategoryLimit(date,category);
        }
    }

    public void leftArrowClick(){
        decrementMonth();

        if(dbHandler.getCategories(viewMonthDate.getValue()).size() == 0){
            incrementMonth();
        }else {

            updateView();
        }
    }

    public void rightArrowClick(){

        incrementMonth();

        if(dbHandler.getCategories(viewMonthDate.getValue()).size() == 0){
            decrementMonth();
        }else {
            updateView();
        }
    }


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



    public LiveData<List<CategoryWithMoney>> getCategories(){ return categoriesLiveData; }

    public LiveData<List<Integer>> getTotalAndSpent(){ return totalAndSpent; }

    public LiveData<Date> getDate(){return viewMonthDate;}
}