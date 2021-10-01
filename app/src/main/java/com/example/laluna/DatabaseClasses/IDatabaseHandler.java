package com.example.laluna.DatabaseClasses;

import java.util.Date;
import java.util.List;

public interface IDatabaseHandler {

    List<Expense> getCategoryExpense (Category category);


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
     * A method for calculating all money spent so far in a certain month.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @return Amount money spent in the whole month in the given date.
     */
    int getTotalMoneySpent(Date date);
    /**
     * A method for calculating all money spent so far in a certain month within a specific category.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @param category represents the category within which the money was spent.
     * @return Amount money spent within the given category during the given month.
     */
    int getTotalSpentByCategory(Date date, Category category);



    /**
     * A method to add a new category to the database.
     */
     Category addCategory(String name, int limit, String pitureName,String color, Date creation);


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

    int getTotalBudget(Date date);
}
