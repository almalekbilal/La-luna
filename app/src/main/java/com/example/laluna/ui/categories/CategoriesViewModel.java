package com.example.laluna.ui.categories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData <List<Category>> categoryMutableLive= new MutableLiveData<>();
    private DBHandler db;


    public void init(Context context) {
        db = new DBHandler(context);
        categoryMutableLive.postValue(db.getCategories(new Date()));
    }


    public LiveData <List<Category>>  getCategory() {
        return categoryMutableLive;
    }
}

