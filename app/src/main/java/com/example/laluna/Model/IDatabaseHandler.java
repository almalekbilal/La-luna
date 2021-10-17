package com.example.laluna.Model;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.Expense;

import java.util.Date;
import java.util.List;

/**
 * The interface represent the classes that is responsible for handling a database
 */
public interface IDatabaseHandler {

    List<Expense> getCategoryExpenseDB(int categoryId);


    // Adding an expense to Data Base
    Expense addExpenseDB(String name, int value, Date date, Category category);


    // Not done yet
    List<Category> getCategoriesDB(Date date);

    //updating expenses table in the database
    void updateExpenseDB(Expense expense);


    void setCategoriesPreviousLimitsDB(Date date);



    List<Expense> getExpensesDB(int start, int end);

    /**
     * A method for deleting an existing expense from the database.
     * @param expense represents the expense that will be deleted.
     */
    void deleteExpenseDB(Expense expense);

    /**
     * A method for calculating all money spent so far in a certain month.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @return Amount money spent in the whole month in the given date.
     */
    int getTotalMoneySpentDB(Date date);
    /**
     * A method for calculating all money spent so far in a certain month within a specific category.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @param category represents the category within which the money was spent.
     * @return Amount money spent within the given category during the given month.
     */
    int getTotalSpentByCategoryDB(Date date, Category category);



    /**
     * A method to add a new category to the database.
     */
     Category addCategoryDB(String name, int limit, int pitureName, String color, Date creation);


    /**
     * A method to deactivate a category
     * @param category Category that will be deactivated
     */

     void deactivateCategoryDB(Category category, Date date);
    /**
     * A method to update category in database
     * @param category Category that will be updating
     */
    void updateCategoryDB(Category category);



    int getCategoryLimitDB(Date date, Category category);

    int getTotalBudgetDB(Date date);
}
