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
    private Date viewMonthDate;

    public AnalysisViewModel() {








    }

    public void init(Context context){
        dbHandler = new DBHandler(context);

        viewMonthDate = new Date();

        categoriesLiveData = new MutableLiveData<>();
        List<Integer> ts = new ArrayList<>();
        ts.add(dbHandler.getTotalMoneySpent(viewMonthDate));
        ts.add(dbHandler.getTotalBudget(viewMonthDate));
        totalAndSpent.postValue(ts);
        updateCategories();
    }

    private void updateCategories(){
        List<CategoryWithMoney> cat = new ArrayList<>();

        List<Category> categories = dbHandler.getCategories(viewMonthDate);

        for(Category category : categories){
            cat.add(new CategoryWithMoney(category, getLimit(category,viewMonthDate) , dbHandler.getTotalSpentByCategory(viewMonthDate,category)));
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
        int month = viewMonthDate.getMonth();

        if(month == 0){
            int year = viewMonthDate.getYear() -1;
            viewMonthDate.setMonth(11);
            viewMonthDate.setYear(year);
        }else{
            viewMonthDate.setMonth(month - 1);
        }



        List<Integer> ts = new ArrayList<>();
        ts.add(dbHandler.getTotalMoneySpent(viewMonthDate));
        ts.add(dbHandler.getTotalBudget(viewMonthDate));
        totalAndSpent.postValue(ts);
        updateCategories();
    }

    public void rightArrowClick(){
        int month = viewMonthDate.getMonth();

        if(month == 12){
            int year = viewMonthDate.getYear() +1;
            viewMonthDate.setMonth(0);
            viewMonthDate.setYear(year);
        }else{
            viewMonthDate.setMonth(month + 1);
        }



        List<Integer> ts = new ArrayList<>();
        ts.add(dbHandler.getTotalMoneySpent(viewMonthDate));
        ts.add(dbHandler.getTotalBudget(viewMonthDate));
        totalAndSpent.postValue(ts);
        updateCategories();

    }



    public LiveData<List<CategoryWithMoney>> getCategories(){ return categoriesLiveData; }

    public LiveData<List<Integer>> getTotalAndSpent(){ return totalAndSpent; }
}