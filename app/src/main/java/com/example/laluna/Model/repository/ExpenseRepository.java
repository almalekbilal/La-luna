package com.example.laluna.Model.repository;

import android.content.Context;

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.Category;
import com.example.laluna.Model.DateConverter;
import com.example.laluna.Model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseRepository {
	
     // Use IDatabaseHandler instead
     private DBHandler db;
     private static ExpenseRepository repo = null;
    //private List<Expense> expenses;

    private ExpenseRepository(Context context) {
        db = new DBHandler(context);
    }

    public List<Expense> getExpenses(int start, int end) {
        List<Expense> expenses = db.getExpenses(start, end);

        return expenses;
    }

    public int getTotalMoneySpend(Date date){
        return Arithmetic.calculateTotalMoneySpent(getExpensesByDate(date));
    }

    public List<Expense> getExpensesByDate(Date date){
        date.setDate(1);
        return getExpensesByStartAndEnd(date, DateConverter.incrementMonth(date));
    }


    public List<Expense> getExpensesByStartAndEnd(Date start, Date end) {
        return db.getExpensesByDates(start, end);
    }


    public void addExpense(String name, int value, Date date, Category category) {
        db.addExpense(name,value,date,category);
    }


    public void deleteExpense(Expense expense) {
        db.deleteExpense(expense);
    }

    public static ExpenseRepository getInstance(Context context){
        if(repo == null){
            repo = new ExpenseRepository(context);
        }

        return repo;
    }

}