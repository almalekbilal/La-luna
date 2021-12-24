package com.example.laluna.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseRepository {

    private DBHandler db;
    //private List<Expense> expenses;


    public ExpenseRepository(Context context) {
        db = new DBHandler(context);

    }

    public List<Expense> getExpenses(int start, int end) {
        List<Expense> expenses = db.getExpenses(start, end);

        return expenses;
    }

    public List<Expense> getExpensesByDate(Date date) {
        List<Expense> expenses = new ArrayList<>();

        // Fel, den kommer att ge alla expenses oavsett datum.
        // Det är fel att använda kategorier för att få expenses när de är inte relaterade.
        // Den borde skötas av en ny dbhandler metod alltså i sql, man kan använda BETWEEN statement i databasen
        for (Category category : db.getCategories(date)) {
            expenses.addAll(db.getCategoryExpense(category.get_id()));
        }
        return expenses;
    }

    // Fixa i databasen istället så att blir effektivare
    public List<Expense> getCategoryExpensesByDate(Category category, Date date) {
        List<Expense> expenses = new ArrayList<>();

        for (Expense expense : db.getCategoryExpense(category.get_id())) {
            if (expense.get_date().getYear() == date.getYear() && expense.get_date().getMonth() == date.getMonth()) {
                expenses.add(expense);
            }
        }
        return expenses;
    }

    // Att hämta alla expense när man lägger ett nytt expense för att ta reda på möjlig id är dålig eftersom den är onödig och gör programmet mycket långsamt
    // id kommer inte att användas ialla fall
    public void addExpense(String name, int value, Date date, Category category) {
        int id = db.getAvailableExpenseId();
        Expense expense = BudgetItemFactory.createExpense(id, name, value, date, category);

        db.addExpense(expense);
    }


    public void deleteExpense(Expense expense) {
        db.deleteExpense(expense);
    }
}