package com.example.laluna.Model;

import java.util.List;

public class Arithmetic {

    public static int calculateTotalMoneySpent(List<Expense> expenseList){     // We can catch Exception here
        int totalMoneySpent = 0;
        for (Expense expense : expenseList){
            totalMoneySpent += expense.get_value();
        }
        return totalMoneySpent;
    }


    public static int calculateTotalBudget(List<CategoryWithExpenses> categoryList){ // We can catch Exception here
        int totalBudget = 0;
        for (CategoryWithExpenses c : categoryList){
            totalBudget += c.getCategoryLimit();
        }
        return totalBudget;
    }
}
