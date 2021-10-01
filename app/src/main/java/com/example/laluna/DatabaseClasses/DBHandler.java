package com.example.laluna.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "laluna.db";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_EXPENSE = "expenses";
    private static final String TABLE_LIMITS = "limits";




    public DBHandler(@Nullable Context context, @Nullable String name,
                     @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCategoryTable = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, limitt INTEGER," +
                "picture_name TEXT, color TEXT, creation_date TEXT, destroyed_date TEXT );";

        String queryExpenseTeble = "CREATE TABLE " + TABLE_EXPENSE + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, value INTEGER, " +
                "date TEXT, category_id INTEGER, " +
                "FOREIGN KEY(category_id) REFERENCES " + TABLE_CATEGORY + "(_id) );";

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



    public List<Expense> getCategoryExpense (Category category){
        List <Expense> expensesList = new ArrayList<Expense>();
        int categoryID=category.get_id();
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




    // Adding an expense to Data Base
    public Expense addExpense(String name, int value, Date date, Category category){
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

    //Convert dateString to Date
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

    private String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


        // Not done yet
    public List<Category> getCategories(Date date){
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
                String pitureName = cursor.getString(cursor.getColumnIndex("picture_name"));
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

    //updating expenses table in the database
    public void updateExpense(Expense expense){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",expense.get_name() );

        contentValues.put("date",dateToString(expense.get_date()));
        contentValues.put("category_id",expense.get_category().get_id());
        contentValues.put("value",expense.get_value());

        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_EXPENSE, contentValues, "_id=?", new String[]{expense.get_id() + ""});
        db.close();
    }



    public void setCategoriesPreviousLimits(Date date){
        List<Category> categories = getCategories(date);

        for(Category cat : categories){
            setCategoryPreviousLimit(cat,date);
        }
    }




    private void setCategoryPreviousLimit(Category category, Date date){


        ContentValues values = new ContentValues();
        values.put("category_id", category.get_id());
        values.put("limitt", category.get_limit());
        values.put("month", dateToString(date));

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LIMITS, null, values);
        db.close();
    }



    public List<Expense> getExpenses(int start, int end){

        ArrayList<Expense> expenses = new ArrayList<Expense>();

        SQLiteDatabase db = getWritableDatabase();

        int limit = end - start;
        String query = "SELECT * FROM " + TABLE_EXPENSE + " LIMIT " + limit + " OFFSET " + start + ";";

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






// Return a specific category by ID
    private Category getCategory(int id){

        String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE _id = " + id + ";";

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        int _id = c.getInt(c.getColumnIndex("_id"));
        int limit = c.getInt(c.getColumnIndex("limitt"));
        String name = c.getString(c.getColumnIndex("name"));
        String pitureName = c.getString(c.getColumnIndex("picture_name"));
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
    public void deleteExpense(Expense expense) {
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
    public int getTotalMoneySpent(Date date){
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
    public int getTotalSpentByCategory(Date date, Category category){
        String query = "SELECT value, date FROM " + TABLE_EXPENSE +
                " WHERE category_id = "+ category.get_id() + " ;";
        final int totalMoney = getTotalMoney(date, query);
        return totalMoney;
    }

    //Helper
    private int getTotalMoney(Date date, String query) {

        int totalMoneySpent = 0;


        final int month = date.getMonth();
        final int year = date.getYear();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            String strDate = cursor.getString(cursor.getColumnIndex("date"));
            String [] splitDate = strDate.split("-");
            if (year == Integer.parseInt(splitDate[0]) && month == Integer.parseInt(splitDate[1])) {
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
    public Category addCategory(String name, int limit, String pitureName,String color, Date creation){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("limitt", limit);
        values.put("picture_name",pitureName);
        values.put("color",color);

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

    public void deactivateCategory(Category category, Date date){

        category.setDestroyedDate(date);
        updateCategory(category);
    }
    /**
     * A method to update category in database
     * @param category Category that will be updating
     */
    public void updateCategory(Category category){
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



    public int getCategoryLimit(Date date, Category category){

        String query = "SELECT limitt FROM " + TABLE_LIMITS + " WHERE category_id = " + category.get_id() + " AND month = " + dateToString(date) + " ;";

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        int limit = c.getInt(c.getColumnIndex("limitt"));

        db.close();

        return limit;
    }

    public int getTotalBudget(Date date){

        int totalBudget = 0;
        String query = "SELECT limitt FROM " + TABLE_LIMITS + " WHERE month = " + dateToString(date) + " ;";

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



 /*try {
final Date stringToDate = new SimpleDateFormat("yyyy-mm-dd").parse(strDate);
final String monthFormat = Integer.toString(stringToDate.getMonth());
final String yearFormat = Integer.toString(stringToDate.getYear());

        if (year.equals(yearFormat) && month.equals(monthFormat)) {
        totalMoneySpent += cursor.getInt(2);

        }
        } catch (ParseException e) {
        e.printStackTrace();
        }

  */
