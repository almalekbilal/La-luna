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


            addExpenses(cat, new Date(), expenseRepository);
            /*
            addExpenses(cat, new Date(121, 2, 15), expenseRepository);
            addExpenses(cat, new Date(121, 3, 15), expenseRepository);
            addExpenses(cat, new Date(121, 4, 15), expenseRepository);
            addExpenses(cat, new Date(121, 5, 15), expenseRepository);
            addExpenses(cat, new Date(121, 6, 15), expenseRepository);
            addExpenses(cat, new Date(121, 7, 15), expenseRepository);
            addExpenses(cat, new Date(121, 8, 15), expenseRepository);
            addExpenses(cat, new Date(121, 9, 15), expenseRepository);
            addExpenses(cat, new Date(121, 10, 15), expenseRepository);
            addExpenses(cat, new Date(121, 0, 15), expenseRepository);
            addExpenses(cat, new Date(121, 11, 15), expenseRepository);

*/

        }

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