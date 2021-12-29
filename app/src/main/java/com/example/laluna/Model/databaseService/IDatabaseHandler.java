package com.example.laluna.Model.databaseService;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.Expense;

import java.util.Date;
import java.util.List;

/**
 * The interface represent the classes that is responsible for handling a database
 */
public interface IDatabaseHandler {




    // Adding an expense to Data Base
    Expense addExpense(String name, int value, Date date, Category category);


    // Not done yet
    List<Category> getCategories(Date date);

    //updating expenses table in the database
    void updateExpense(Expense expense);


    void setCategoriesPreviousLimits(Date date);



    List<Expense> getExpenses(int start, int end);

    /**
     * A method for deleting an existing expense from the database.
     * @param expense represents the expense that will be deleted.
     */
    void deleteExpense(Expense expense);





    /**
     * A method to add a new category to the database.
     */
     Category addCategory(String name, int limit, int pitureName, String color, Date creation);


    /**
     * A method to deactivate a category
     * @param category Category that will be deactivated
     */

     void deactivateCategory(Category category, Date date);
    /**
     * A method to update category in database
     * @param category Category that will be updating
     */
    void updateCategory(Category category);



    int getCategoryLimit(Date date, Category category);



    boolean thereIsCategories();

    List<Expense> getExpensesByDates(Date start, Date end);

    List<Expense> getCategoryExpensesByDate(Date start, Date end, Category category);
}
