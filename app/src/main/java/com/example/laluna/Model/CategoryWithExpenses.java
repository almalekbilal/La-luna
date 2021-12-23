package com.example.laluna.Model;

import java.util.Date;
import java.util.List;

public class CategoryWithExpenses extends Category{


    private List<Expense> categoryExpenses;

    public CategoryWithExpenses(List<Expense> categoryExpenses, int _id, int _limit, String _name, int _pictureName, String _color, Date creationDate, Date destroyedDate) {
        super(_id, _limit, _name, _pictureName, _color, creationDate, destroyedDate);
        this.categoryExpenses = categoryExpenses;
    }

    public int getSpentMoney(){
        int categorySpentMoney = Arithmetic.calculateTotalMoneySpent(categoryExpenses);
        return categorySpentMoney;
    }

    public List<Expense> getCategoryExpenses() {
        return categoryExpenses;
    }
}
