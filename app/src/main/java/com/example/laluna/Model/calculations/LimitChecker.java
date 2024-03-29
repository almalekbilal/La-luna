package com.example.laluna.Model.calculations;

import com.example.laluna.Model.categoryAndExpense.CategoryWithExpenses;

import java.util.List;

public class LimitChecker{

	
    public boolean categoryOverLimit(CategoryWithExpenses categoryWithExpenses){
        int categoryLimit = categoryWithExpenses.getCategoryLimit();
        int categorySpentMoney = categoryWithExpenses.getSpentMoney();

        if(categorySpentMoney >= categoryLimit){
            return true;
        }
        return false;
    }


    public boolean totalBudgetOverLimit(List<CategoryWithExpenses> categories){
        int totalBudget = Arithmetic.calculateTotalBudget(categories);

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
