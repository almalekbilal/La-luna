package com.example.laluna.Model;

import com.example.laluna.Model.repository.DBHandler;

import java.util.Date;
import java.util.List;

public class CategoryWithExpenses {

    private Category category;
    private int limit;
    private List<Expense> categoryExpenses;

    public CategoryWithExpenses(Category category, List<Expense> categoryExpenses, int limit){
        this.category = category;
        this.categoryExpenses = categoryExpenses;
        this.limit = limit;
    }

    public int getSpentMoney(){
        int categorySpentMoney = Arithmetic.calculateTotalMoneySpent(categoryExpenses);
        return categorySpentMoney;
    }

    public int getCategoryLimit(){
        return limit;
    }

    public List<Expense> getCategoryExpenses() {
        return categoryExpenses;
    }

    public Category getCategory(){
        return category;
    }
}
