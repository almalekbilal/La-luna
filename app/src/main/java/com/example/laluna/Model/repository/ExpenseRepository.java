package com.example.laluna.Model.repository;

import android.content.Context;

import com.example.laluna.Model.calculations.Arithmetic;
import com.example.laluna.Model.categoryAndExpense.Category;
import com.example.laluna.Model.DateConverter;
import com.example.laluna.Model.categoryAndExpense.Expense;
import com.example.laluna.Model.databaseService.IDatabaseHandler;
import com.example.laluna.Model.databaseService.SqliteHandler;
import com.example.laluna.Model.exceptions.ExpenseIrrelevantNameException;
import com.example.laluna.Model.exceptions.ExpenseIrrelevantValueException;

import java.util.Date;
import java.util.List;

public class ExpenseRepository {
	
     // Use IDatabaseHandler instead
     private IDatabaseHandler db;
     private static ExpenseRepository repo = null;
    //private List<Expense> expenses;

    private ExpenseRepository(Context context) {
        db = db = new SqliteHandler(context, null, null, 0);
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


    public Expense addExpense(String name, int value, Date date, Category category) {
        try {
            if(value < 0){
                throw new ExpenseIrrelevantValueException();
            }
            if(name.equals("") || name == null){
                throw new ExpenseIrrelevantNameException();
            }
        }catch (ExpenseIrrelevantValueException e) {
            e.printStackTrace();
        }catch (ExpenseIrrelevantNameException e) {
            e.printStackTrace();
        }


        return db.addExpense(name,value,date,category);
    }

    public void updateExpense(Expense expense){
        db.updateExpense(expense);
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