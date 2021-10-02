package com.example.laluna.DatabaseClasses;

import android.content.Context;

import java.util.Date;
import java.util.List;

public class DBHandler {

    private Context context;
    private IDatabaseHandler db;

    public DBHandler(Context context) {
        this.context = context;

        db = new SqliteHandler(context, null,null,1);
    }

    public List<Expense> getCategoryExpense (Category category){
        return db.getCategoryExpenseDB(category);
    }




    // Adding an expense to Data Base
    public Expense addExpense(String name, int value, Date date, Category category){
        return db.addExpenseDB(name,value,date,category);
    }



    // Not done yet
    public List<Category> getCategories(Date date){
        return db.getCategoriesDB(date);
    }


    //updating expenses table in the database
    public void updateExpense(Expense expense){
        db.updateExpenseDB(expense);
    }



    public void setCategoriesPreviousLimits(Date date){
      db.setCategoriesPreviousLimitsDB(date);
    }




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
    public Category addCategory(String name, int limit, String pitureName,String color, Date creation){
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



    public int getCategoryLimit(Date date, Category category){

        return db.getCategoryLimitDB(date,category);
    }

    public int getTotalBudget(Date date){

        return db.getTotalBudgetDB(date);
    }
}


