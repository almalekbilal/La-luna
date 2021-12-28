package com.example.laluna.ui;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.repository.DBHandler;
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
        DBHandler db = new DBHandler(context);


        if (!db.thereIsCategories()) {

            List<Category> cat = new ArrayList<>();

            Category food = db.addCategory("Food", 1000, R.drawable.food, "#09F9BF", new Date());
            Category clothes = db.addCategory("Clothes", 1000, R.drawable.clothes, "#03A9F1", new Date());
            Category house = db.addCategory("House", 1000, R.drawable.home, "#dd6800", new Date());
            Category entertainment = db.addCategory("Entertainment", 1000, R.drawable.entertainment, "#B2378E", new Date());
            Category health = db.addCategory("Health", 1000, R.drawable.health, "#f30008", new Date());
            Category other = db.addCategory("Other", 1000, R.drawable.other, "#737274", new Date());


            cat.add(food);
            cat.add(clothes);
            cat.add(house);
            cat.add(entertainment);
            cat.add(health);
            cat.add(other);

            addExpenses(cat, new Date(), db);

        }

    }

    private void addExpenses(List<Category> cat, Date date, DBHandler handler) {

        handler.addExpense("MAX Burger", 100, date, cat.get(0));
        handler.addExpense("Pizza", 100, date, cat.get(0));
        handler.addExpense("Tikka masala", 100, date, cat.get(0));
        handler.addExpense("Godis", 100, date, cat.get(0));

        handler.addExpense("T shirt", 100, date, cat.get(1));
        handler.addExpense("Skor", 100, date, cat.get(1));
        handler.addExpense("Tröja", 100, date, cat.get(1));


        handler.addExpense("Lampor", 100, date, cat.get(2));
        handler.addExpense("Lampor", 100, date, cat.get(2));
        handler.addExpense("Lampor", 100, date, cat.get(2));


        handler.addExpense("Cs", 500, date, cat.get(3));
        handler.addExpense("Skarasommarland", 500, date, cat.get(3));
        handler.addExpense("Liseberg", 500, date, cat.get(3));


        handler.addExpense("Bil olja", 2000, date, cat.get(5));
        handler.addExpense("Tandläkare", 700, date, cat.get(4));
        handler.addExpense("Liseberg", 500, date, cat.get(3));
        handler.addExpense("Lampor", 100, date, cat.get(2));
        handler.addExpense("Jacka", 100, date, cat.get(1));
        handler.addExpense("Fisk", 100, date, cat.get(0));

    }
}