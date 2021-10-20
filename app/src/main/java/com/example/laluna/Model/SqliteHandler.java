package com.example.laluna.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.Expense;
import com.example.laluna.Model.IDatabaseHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A class for communication with the local Sqlite database
 *
 *  @auther (Bilal Al Malek)
 *  @auther (Deaa Khankan)
 *  @auther (Ali Malla)
 *  @auther (Ali Al Khaled)
 *
 */
public class SqliteHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "laluna.db";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_EXPENSE = "expenses";
    private static final String TABLE_LIMITS = "limits";




    public SqliteHandler(@Nullable Context context, @Nullable String name,
                         @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // This method creates the tables in databas
    @Override
    public void onCreate(SQLiteDatabase db) {
        // The first table is categories table and will hold all the categories information in it
        String queryCategoryTable = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, limitt INTEGER," +
                "picture_name INTEGER, color TEXT, creation_date TEXT, destroyed_date TEXT );";

        // The second table is expenses table and will hold all the expenses information in it
        String queryExpenseTeble = "CREATE TABLE " + TABLE_EXPENSE + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, value INTEGER, " +
                "date TEXT, category_id INTEGER, " +
                "FOREIGN KEY(category_id) REFERENCES " + TABLE_CATEGORY + "(_id) );";

        // The third table will hold information about the limits of categories in previous months
        String queryLimitTable = "CREATE TABLE " + TABLE_LIMITS + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, month TEXT, category_id INTEGER, limitt INTEGER, " +
                "FOREIGN KEY(category_id) REFERENCES " + TABLE_CATEGORY + "(_id) );";

        db.execSQL(queryCategoryTable);
        db.execSQL(queryExpenseTeble);
        db.execSQL(queryLimitTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIMITS);

        onCreate(db);
    }


    /**
     * A method for getting all the expenses of a a specific category.
     * @param categoryID represents the category that the method will get all its related expenses.
     */
    public List<Expense> getCategoryExpenseDB(int categoryID){
        List <Expense> expensesList = new ArrayList<Expense>();

        String query = "SELECT *  FROM " + TABLE_EXPENSE + " WHERE category_id = "
                + categoryID + ";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));

            int expenseValue = cursor.getInt(
                        cursor.getColumnIndex("value") );


            Date expenseDate = stringToDate(cursor.getString(cursor.getColumnIndex("date")));

            Category expenseCategory = getCategory(cursor.getInt(cursor.getColumnIndex("category_id")));

                expensesList.add(new Expense(id,name,expenseValue, expenseDate, expenseCategory));
                cursor.moveToNext();
        }
        db.close();
        return expensesList;
    }




    /**
     * A method that adds a new expense into the database
     * @param name that represent the name of the new expense.
     * @param value that represent the value of the new expense.
     * @param date that represent the creation date of the expense
     * @param category that represent the category which the expense will belong to
     */
    public Expense addExpenseDB(String name, int value, Date date, Category category){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("value", value);

        values.put("date",dateToString(date));

        values.put("category_id", category.get_id());

        SQLiteDatabase db = getWritableDatabase();

        int id = (int) db.insert(TABLE_EXPENSE, null, values);
        Expense expense = new Expense(id,name,value,date,category);
        db.close();

        return expense;
    }

    /**
     * A method that takes a string and converts it to date object
     * The method is private and will be used only here in this class
     */
    private Date stringToDate(String dateString){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if(dateString != null) {
                date = sdf.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * A method that takes a date object and converts it to a string value
     * The method is private and will be used only here in this class
     */
    private String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * A method for getting all the active categories in a specific month
     * @param date represent the month date.
     */
    public List<Category> getCategoriesDB(Date date){
        List <Category> categories = new ArrayList<>();


        String query = "SELECT * FROM " + TABLE_CATEGORY + " ;";

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        while(!cursor.isAfterLast()){

            if(isBetween(date,cursor)){

                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                int limit = cursor.getInt(cursor.getColumnIndex("limitt"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int pitureName = cursor.getInt(cursor.getColumnIndex("picture_name"));
                String color = cursor.getString(cursor.getColumnIndex("color"));
                Date creationDate = stringToDate(cursor.getString(cursor.getColumnIndex("creation_date")));
                Date destroyedDate = stringToDate(cursor.getString(cursor.getColumnIndex("destroyed_date")));


                categories.add(new Category(_id,limit,name,pitureName,color,creationDate,destroyedDate));
            }

            cursor.moveToNext();
        }
        db.close();
        return categories;
    }


    /**
     * The method checks if the date is between creation and destroy dates in category row
     * The method is private and will be used only in this class
     */
    private boolean isBetween(Date date, Cursor cursor){

        boolean IsBetween = false;

        if(cursor.isNull(cursor.getColumnIndex("creation_date"))){
            IsBetween = true;
        }else{
            Date creationDate = stringToDate(cursor.getString(cursor.getColumnIndex("creation_date")));
            Date destroyedDate;
            if(cursor.isNull(cursor.getColumnIndex("destroyed_date"))){
                destroyedDate = new Date();
            }else{
                destroyedDate = stringToDate(cursor.getString(cursor.getColumnIndex("destroyed_date")));
            }

            IsBetween = date.compareTo(destroyedDate) <= 0 && date.compareTo(creationDate) >= 0;
        }

        return IsBetween;

    }

    /**
     * A method for updating an expense in the database
     * @param expense is the expense object that contains new values that will be updated
     */
    public void updateExpenseDB(Expense expense){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",expense.get_name() );

        contentValues.put("date",dateToString(expense.get_date()));
        contentValues.put("category_id",expense.get_category().get_id());
        contentValues.put("value",expense.get_value());

        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_EXPENSE, contentValues, "_id=?", new String[]{String.valueOf(expense.get_id())});
        db.close();
    }


    /**
     * A method for saving the limits of the active categories for the past month
     * @param date represent the month
     */
    public void setCategoriesPreviousLimitsDB(Date date){
        List<Category> categories = getCategoriesDB(date);

        for(Category cat : categories){
            setCategoryPreviousLimit(cat,date);
        }
    }




    private void setCategoryPreviousLimit(Category category, Date date){


        ContentValues values = new ContentValues();
        date.setDate(1);
        values.put("category_id", category.get_id());
        values.put("limitt", category.get_limit());
        values.put("month", dateToString(date));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LIMITS, null, values);
        db.close();
    }


    /**
     * A method for getting expenses from the database
     * @param start represent the row number of the Expenses table in database that the algorithm will start slicing
     * @param end represent the row number of the Expenses table in database that the algorithm will finish slicing
     */
    public List<Expense> getExpensesDB(int start, int end){

        ArrayList<Expense> expenses = new ArrayList<Expense>();

        SQLiteDatabase db = getWritableDatabase();

        int limit = end - start;
        String query = "SELECT * FROM " + TABLE_EXPENSE + " ORDER BY _id DESC LIMIT " + limit + " OFFSET " + start + ";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){

            int id = c.getInt(c.getColumnIndex("_id"));
            int value = c.getInt(c.getColumnIndex("value"));
            String name = c.getString(c.getColumnIndex("name"));
            Date date = stringToDate(c.getString(c.getColumnIndex("date")));
            Category cat = getCategory(c.getInt(c.getColumnIndex("category_id")));

            Expense exp = new Expense(id,name,value,date,cat);
            expenses.add(exp);

            c.moveToNext();
        }

        return expenses;

    }




    /**
     * A method for getting a category from database
     * it take the category from database and transform it to java object Category
     * its private and will be used only here in this class
     * @param id represents the categery id in database
     */

    private Category getCategory(int id){

        String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE _id = " + id + ";";

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        int _id = c.getInt(c.getColumnIndex("_id"));
        int limit = c.getInt(c.getColumnIndex("limitt"));
        String name = c.getString(c.getColumnIndex("name"));
        int pitureName = c.getInt(c.getColumnIndex("picture_name"));
        String color = c.getString(c.getColumnIndex("color"));
        Date creationDate = stringToDate(c.getString(c.getColumnIndex("creation_date")));
        Date destroyedDate = stringToDate(c.getString(c.getColumnIndex("destroyed_date")));


        Category category = new Category(_id,limit,name,pitureName,color,creationDate,destroyedDate);
        db.close();
        return category;
    }


    /**
     * A method for deleting an existing expense from the database.
     * @param expense represents the expense that will be deleted.
     */
    public void deleteExpenseDB(Expense expense) {
        final int id = expense.get_id();
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM "+ TABLE_EXPENSE + " WHERE _id=" + Integer.toString(id) + ";" );
        db.close();
    }


    /**
     * A method for calculating all money spent so far in a certain month.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @return Amount money spent in the whole month in the given date.
     */
    public int getTotalMoneySpentDB(Date date){
        String query = "SELECT value, date FROM " + TABLE_EXPENSE + " ;";
        final int totalMoney = getTotalMoney(date, query);
        return totalMoney;
    }

    /**
     * A method for calculating all money spent so far in a certain month within a specific category.
     * @param date represents the date in which the money was spent.
     *             The days in the given date have no significance in the result in this case.
     * @param category represents the category within which the money was spent.
     * @return Amount money spent within the given category during the given month.
     */
    public int getTotalSpentByCategoryDB(Date date, Category category){
        String query = "SELECT value, date FROM " + TABLE_EXPENSE +
                " WHERE category_id = "+ category.get_id() + " ;";
        final int totalMoney = getTotalMoney(date, query);
        return totalMoney;
    }

    //Helper
    private int getTotalMoney(Date date, String query) {

        int totalMoneySpent = 0;


        final int month = date.getMonth();
        final int year = date.getYear() + 1900;

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            String strDate = cursor.getString(cursor.getColumnIndex("date"));
            String [] splitDate = strDate.split("-");
            if (year == Integer.parseInt(splitDate[0]) && month == Integer.parseInt(splitDate[1])-1) {
                totalMoneySpent += cursor.getInt(cursor.getColumnIndex("value"));
            }

            cursor.moveToNext();
        }

        db.close();

        return totalMoneySpent;
    }


    /**
     * A method to add a new category to the database.
     */
    public Category addCategoryDB(String name, int limit, int pitureName, String color, Date creation){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("limitt", limit);
        values.put("picture_name",pitureName);
        values.put("color",color);

        creation.setDate(1);
        if(creation != null) {
            values.put("creation_date", dateToString(creation));
        }

        SQLiteDatabase db = getWritableDatabase();

        int id = (int)db.insert(TABLE_CATEGORY,null,values);

        Category newCategory = new Category(id, limit,name,pitureName,color,creation,null);

        db.close();

        return newCategory;
    }


    /**
     * A method to deactivate a category
     * @param category Category that will be deactivated
     */

    public void deactivateCategoryDB(Category category, Date date){

        category.setDestroyedDate(date);
        updateCategoryDB(category);
    }
    /**
     * A method to update category in database
     * @param category Category that will be updating
     */
    public void updateCategoryDB(Category category){
        ContentValues values = new ContentValues();
        values.put("name", category.get_name());
        values.put("limitt", category.get_limit());
        values.put("picture_name",category.get_pictureName());
        values.put("color",category.get_color());
        if(category.getDestroyedDate() != null) {
            values.put("destroyed_date", dateToString(category.getDestroyedDate()));
        }

        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_CATEGORY,values,"_id=?", new String[]{category.get_id() + ""});
        db.close();
    }


    /**
     * A method for getting a limit of a specific category in a past month from the limits table in the database
     * @param date represent the month
     * @param category represent the category which limit will gets
     */
    public int getCategoryLimitDB(Date date, Category category){

        String query = "SELECT limitt FROM " + TABLE_LIMITS + " WHERE category_id = " + category.get_id() + " AND month = '" + dateToString(date) + "' ;";

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        int limit = c.getInt(c.getColumnIndex("limitt"));

        db.close();

        return limit;
    }

    /**
     * A method for getting the budget of a specific month, it gets all the category limits of the month and adding them
     * @param date represent the month
     */
    public int getTotalBudgetDB(Date date){

        Date thisDate = new Date();
        if(thisDate.getYear() == date.getYear() && thisDate.getMonth() == date.getMonth()){
            return getTotalBudgetThisMonth(date);
        }else{
            return getTotalBudgetPreviousMonth(date);
        }

    }

    private int getTotalBudgetThisMonth(Date date){
        int totalBudget = 0;

        List<Category> categories = getCategoriesDB(date);

        for(Category category : categories){
            totalBudget += category.get_limit();
        }
        return totalBudget;
    }


    private int getTotalBudgetPreviousMonth(Date date){
        int totalBudget = 0;
        date.setDate(1);
        String query = "SELECT limitt FROM " + TABLE_LIMITS + " WHERE month = '" + dateToString(date) + "' ;";

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            int limit = c.getInt(c.getColumnIndex("limitt"));
            totalBudget += limit;
            c.moveToNext();
        }

        db.close();


        return totalBudget;
    }
}

