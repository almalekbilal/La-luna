package com.example.laluna.ui;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
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


         //   addExpenses(cat, new Date(), expenseRepository);

            addExpense(cat, new Date(121, 11, 30), expenseRepository, 2567);
            addExpense(cat, new Date(121, 11, 23), expenseRepository, 2000);
            addExpense(cat, new Date(121, 11, 16), expenseRepository, 1400);
            addExpense(cat, new Date(121, 11, 9), expenseRepository, 4000);
            addExpense(cat, new Date(121, 11, 2), expenseRepository, 2999);
            addExpense(cat, new Date(121, 10, 25), expenseRepository, 4500);
            addExpense(cat, new Date(121, 10, 18), expenseRepository, 1000);
            addExpense(cat, new Date(121, 10, 11), expenseRepository, 2000);
            addExpense(cat, new Date(121, 10, 4), expenseRepository, 3000);
            addExpense(cat, new Date(121, 9, 28), expenseRepository, 4500);
         //   addExpenses(cat, new Date(121, 11, 24), expenseRepository);




        }

    }

    private void addExpense(List<Category> cat, Date date, ExpenseRepository expenseRepository, int value){
        expenseRepository.addExpense("MAX Burger", value, date, cat.get(0));
    }

    private void addExpenses(List<Category> cat, Date date, ExpenseRepository expenseRepository) {

        expenseRepository.addExpense("MAX Burger", 100, date, cat.get(0));
        expenseRepository.addExpense("Pizza", 100, date, cat.get(0));
        expenseRepository.addExpense("Tikka masala", 100, date, cat.get(0));
        expenseRepository.addExpense("Godis", 100, date, cat.get(0));

        expenseRepository.addExpense("T shirt", 100, date, cat.get(1));
        expenseRepository.addExpense("Skor", 100, date, cat.get(1));
        expenseRepository.addExpense("Tröja", 100, date, cat.get(1));


        expenseRepository.addExpense("Lampor", 100, date, cat.get(2));
        expenseRepository.addExpense("Lampor", 100, date, cat.get(2));
        expenseRepository.addExpense("Lampor", 100, date, cat.get(2));



        expenseRepository.addExpense("Skarasommarland", 500, date, cat.get(3));
        expenseRepository.addExpense("Liseberg", 500, date, cat.get(3));


        expenseRepository.addExpense("Bil olja", 2000, date, cat.get(5));
        expenseRepository.addExpense("Tandläkare", 700, date, cat.get(4));
        expenseRepository.addExpense("Liseberg", 500, date, cat.get(3));
        expenseRepository.addExpense("Lampor", 100, date, cat.get(2));
        expenseRepository.addExpense("Jacka", 100, date, cat.get(1));
        expenseRepository.addExpense("Fisk", 100, date, cat.get(0));


    }
}