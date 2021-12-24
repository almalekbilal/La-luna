package com.example.laluna.Model;

import java.util.List;

public class Arithmetic {

    public static int calculateTotalMoneySpent(List<Expense> expenseList){
        int totalMoneySpent = 0;
        for (Expense expense : expenseList){
            totalMoneySpent += expense.get_value();
        }
        return totalMoneySpent;
    }

    // Fel, det är inte alltid man hittar limit i get_limit.
    // Om man tittar i föregående månader så hittar man de i den tredje tabellens
    public static int calculateTotalBudget(List<? extends Category> categoryList){
        int totalBudget = 0;
        for (Category category : categoryList){
            totalBudget += category.get_limit();
        }
        return totalBudget;
    }
}
