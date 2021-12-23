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
 * @auther (Ali AlKhaled)
 */
public class DBHandler {


    private Context context;
    // It contains an object of the interface ISQLiteHandler, in this case its SqliteHandler
    private ISQLiteHandler db;


    public DBHandler(Context context) {
        this.context = context;

        db = new SqliteHandler(context, null,null,1);
    }

    /**
     * A method for getting all the expenses of a a specific category.
     * @param categoryId represents the category that the method will get all its related expenses.
     */
    public List<Expense> getCategoryExpense (int categoryId){
        return db.getCategoryExpenseDB(categoryId);
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
     * A method to add a new category to the database.
     */
    public Category addCategory(String name, int limit, int pictureName,String color, Date creation){
        return db.addCategoryDB(name,limit,pictureName,color,creation);
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





    ///////////////////////////////////////////////////////////////////////////////////
    public int getTotalMoneySpent(Date date) {
        return db.getTotalMoneySpentDB(date);
    }

    public int getTotalBudget(Date date) {
        return db.getTotalMoneySpentDB(date);
    }

    public boolean thereIsCategories(Date date) {
        return db.thereIsCategoriesDB(date);
    }
/////////////////////////////////////////////////////////////////////////////////////////







    public void addExpense(Expense expense) {
        db.addExpenseDB(expense);
    }

    public int getAvailableExpenseId(){
        return db.getAvailableExpenseId();
    }
}


