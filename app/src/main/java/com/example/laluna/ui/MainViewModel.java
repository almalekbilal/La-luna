package com.example.laluna.ui;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.categoryAndExpense.Category;
import com.example.laluna.Model.repository.CategoryRepository;
import com.example.laluna.Model.repository.ExpenseRepository;
import com.example.laluna.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainViewModel extends ViewModel {

    Context context;
    public void init(Context context){

        this.context = context;


        insertToDB();
    }
    private void insertToDB() {

        CategoryRepository categoryRepository = CategoryRepository.getInstance(context);
        ExpenseRepository expenseRepository = ExpenseRepository.getInstance(context);


        if (!categoryRepository.thereIsCategories()) {

            List<Category> cat = new ArrayList<>();

            Category food = categoryRepository.addCategory("Food", 1000, R.drawable.food, "#09F9BF", new Date());
            Category clothes = categoryRepository.addCategory("Clothes", 1000, R.drawable.clothes, "#03A9F1", new Date());
            Category house = categoryRepository.addCategory("House", 1000, R.drawable.home, "#dd6800", new Date());
            Category entertainment = categoryRepository.addCategory("Entertainment", 1000, R.drawable.entertainment, "#B2378E", new Date());
            Category health = categoryRepository.addCategory("Health", 1000, R.drawable.health, "#f30008", new Date());
            Category other = categoryRepository.addCategory("Other", 1000, R.drawable.other, "#737274", new Date());


            cat.add(food);
            cat.add(clothes);
            cat.add(house);
            cat.add(entertainment);
            cat.add(health);
            cat.add(other);



// test data
            expenseRepository.addExpense("MAX Burger", 400, new Date(122, 7, 22), food);
            expenseRepository.addExpense("MAX Burger", 700, new Date(122, 6, 22), clothes);
            expenseRepository.addExpense("MAX Burger", 600, new Date(122, 5, 22), house);
            expenseRepository.addExpense("MAX Burger", 300, new Date(122, 4, 22), entertainment);
            expenseRepository.addExpense("MAX Burger", 550, new Date(122, 3, 22), health);



        }

    }


}