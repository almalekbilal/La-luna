package com.example.laluna.Model;

import java.util.Date;
import java.util.List;

public class BudgetItemFactory {

    public static Expense createExpense(int id, String name, int value, Date date, Category category) {
        return new Expense(id, name, value, date, category);
    }

    public static Category createCategory(int _id, int _limit, String _name, int _pictureName, String _color, Date creationDate, Date destroyedDate) {
        return new Category(_id, _limit, _name, _pictureName, _color, creationDate, destroyedDate);
    }

    public static CategoryWithExpenses createCategoryWithExpenses(List<Expense> categoryExpenses, int _id, int _limit, String _name, int _pictureName, String _color, Date creationDate, Date destroyedDate) {
        return new CategoryWithExpenses(categoryExpenses, _id, _limit, _name, _pictureName, _color, creationDate, destroyedDate);
    }
}
