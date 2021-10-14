package com.example.laluna.Model;

import android.content.Context;

import java.util.Date;
import java.util.List;

// Class DBHandler is the database handler class that will handle the communication between the viewmodel and the database

/**
 * A class for communication with the database
 *
 * @auther (Bilal Al Malek)
 * @auther (Deaa Khankan)
 * @auther (Ali Malla)
 * @auther (Ali Al Khaled)
 */
public class DBHandler {


    private Context context;
    // It contains an object of the interface IDatabaseHandler, in this case its SqliteHandler
    private IDatabaseHandler db;


    public DBHandler(Context context) {
        this.context = context;

        db = new SqliteHandler(context, null,null,1);
    }

    /**
     * A method for getting all the expenses of a a specific category.
     * @param category represents the category that the method will get all its related expenses.
     */
    public List<Expense> getCategoryExpense (Category category){
        return db.getCategoryExpenseDB(category);
    }



    /**
     * A method that adds a new expense into the database
     * @param name that represent the name of the new expense.
     * @param value that represent the value of the new expense.
     * @param date that represent the creation date of the expense
     * @param category that represent the category which the expense will belong to
     */
    public Expense addExpense(String name, int value, Date date, Category category){
        return db.addExpenseDB(name,value,date,category);
    }



    /**
     * A method for getting all the active categories in a specific month
     * @param date represent the month date.
     */
    public List<Category> getCategories(Date date){
        return db.getCategoriesDB(date);
    }


    /**
     * A method for updating an expense in the database
     * @param expense is the expense object that contains new values that will be updated
     */
    public void updateExpense(Expense expense){
        db.updateExpenseDB(expense);
    }


    /**
     * A method for saving the limits of the active categories for the past month
     * @param date represent the month
     */
    public void setCategoriesPreviousLimits(Date date){
      db.setCategoriesPreviousLimitsDB(date);
    }



    /**
     * A method for getting expenses from the database
     * @param start represent the row number of the Expenses table in database that the algorithm will start slicing
     * @param end represent the row number of the Expenses table in database that the algorithm will finish slicing
     */
    public List<Expense> getExpenses(int start, int end){
        return db.getExpensesDB(start, end);

    }


    /**
     * A method for deleting an existing expense from the database.
     * @param expense represents the expense that will be deleted.
     */
    public void deleteExpense(Expense expense) {
        db.deleteExpenseDB(expense);
    }


    /**
     * A method for calculating all money spent so far in a certain month.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @return Amount money spent in the whole month in the given date.
     */
    public int getTotalMoneySpent(Date date){
        return db.getTotalMoneySpentDB(date);
    }

    /**
     * A method for calculating all money spent so far in a certain month within a specific category.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @param category represents the category within which the money was spent.
     * @return Amount money spent within the given category during the given month.
     */
    public int getTotalSpentByCategory(Date date, Category category){
     return db.getTotalSpentByCategoryDB(date, category);
    }



    /**
     * A method to add a new category to the database.
     */
    public Category addCategory(String name, int limit, int pitureName,String color, Date creation){
        return db.addCategoryDB(name,limit,pitureName,color,creation);
    }


    /**
     * A method to deactivate a category
     * @param category Category that will be deactivated
     */

    public void deactivateCategory(Category category, Date date){
        db.deactivateCategoryDB(category, date);
    }
    /**
     * A method to update category in database
     * @param category Category that will be updating
     */
    public void updateCategory(Category category){
        db.updateCategoryDB(category);
    }


    /**
     * A method for getting a limit of a specific category in a past month from the limits table in the database
     * @param date represent the month
     * @param category represent the category which limit will gets
     */
    public int getCategoryLimit(Date date, Category category){

        return db.getCategoryLimitDB(date,category);
    }

    /**
     * A method for getting the budget of a specific month, it gets all the category limits of the month and adding them
     * @param date represent the month
     */
    public int getTotalBudget(Date date){

        return db.getTotalBudgetDB(date);
    }
}


