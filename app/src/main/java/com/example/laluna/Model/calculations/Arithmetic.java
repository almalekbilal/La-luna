package com.example.laluna.Model.calculations;

import com.example.laluna.Model.categoryAndExpense.CategoryWithExpenses;
import com.example.laluna.Model.categoryAndExpense.Expense;

import java.util.List;

public class Arithmetic {

    public static int calculateTotalMoneySpent(List<Expense> expenseList){
        int totalMoneySpent = 0;
        for (Expense expense : expenseList){
            totalMoneySpent += expense.get_value();
        }
        return totalMoneySpent;
    }


    public static int calculateTotalBudget(List<CategoryWithExpenses> categoryList){
        int totalBudget = 0;
        for (CategoryWithExpenses c : categoryList){
            totalBudget += c.getCategoryLimit();
        }
        return totalBudget;
    }
}
