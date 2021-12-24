package com.example.laluna.Model;

import java.util.List;

public class LimitChecker{


    public boolean categoryOverLimit(CategoryWithExpenses categoryWithExpenses){
        int categoryLimit = categoryWithExpenses.get_limit();
        int categorySpentMoney = categoryWithExpenses.getSpentMoney();

        if(categorySpentMoney >= categoryLimit){
            return true;
        }
        return false;
    }


    public boolean totalBudgetOverLimit(List<CategoryWithExpenses> categories){
        int totalBudget = Arithmetic.calculateTotalBudget(categories);  // Fel med beräkning av totalbudget, jag skrev det i Aritmetic

        int totalSpentMoney = 0;
        for(CategoryWithExpenses categoryWithExpenses : categories){
            totalSpentMoney += categoryWithExpenses.getSpentMoney();
        }

        if(totalSpentMoney >= totalBudget){
            return true;
        }

        return false;
    }
}
