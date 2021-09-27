package com.example.laluna.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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


    private  SQLiteDatabase db = getWritableDatabase();


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
        String query = "SELECT *  FROM " + TABLE_EXPENSE + "WHERE category_id ="
                + categoryID + ";";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));

            int expenseValue = cursor.getInt(
                        cursor.getColumnIndex("value") );

            String []dateString = cursor.getString
                    (cursor.getColumnIndex("date")).split("-");

            Date expenseDate = stringToDate(dateString);

                expensesList.add(new Expense(id,name,expenseValue, expenseDate, category));
                cursor.moveToNext();
        }
        db.close();
        return expensesList;
    }






    // Adding an expense to Data Base
    public void addExpense(Expense expense){
        ContentValues values = new ContentValues();
        values.put("name", expense.get_name());
        values.put("value", expense.get_value());
        values.put("date",expense.get_date().toString());
        values.put("category_id", expense.get_category().get_id());

        db.insert(TABLE_EXPENSE,null,values);
        db.close();
    }






    //Convert dateString to Date
    private Date stringToDate(String dateString[] ){
        return new Date (Integer.parseInt(dateString[0]) - 1900
                    , Integer.parseInt(dateString[1]) -1,
                Integer.parseInt(dateString[2]));
    }





        // Not done yet
    public List<Category> getCategories(Date date){
        List <Category> categories = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CATEGORY + "WHERE" + date +
                "BETWEEN  creation_date AND destroyed_date;";

        Cursor cursor= db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

             int _id =cursor.getInt(cursor.getColumnIndex("_id"));
             int _limit = cursor.getInt(cursor.getColumnIndex("limitt"));

             Date creationDate = stringToDate(cursor.getString(cursor.getColumnIndex(" creation_date")).split("-"));
             Date destroyedDate= stringToDate(cursor.getString(cursor.getColumnIndex(" destroyed_date")).split("-"));
             String _name = cursor.getString(cursor.getColumnIndex("name"));
             String _pictureName = cursor.getString(cursor.getColumnIndex("picture_name"));
             String _color = cursor.getString(cursor.getColumnIndex("color"));

             categories.add(new Category(_id,_limit,_name, _pictureName, _color, creationDate, destroyedDate ));
            cursor.moveToNext();
        }
        db.close();
        return categories;
    }





    //updating expenses table in the database
    public void updateExpense(Expense expense){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id",expense.get_id());
        contentValues.put("name",expense.get_name() );
        contentValues.put("date",expense.get_date().toString());
        contentValues.put("category_id",expense.get_category().get_id());
        contentValues.put("id",expense.get_value());
    }







    public void setCategoriesPreviousLimits(Date date){
        List<Category> categories = getCategories(date);

        for(Category cat : categories){
            setCategoryPreviousLimit(cat,date);
        }
    }







    private void setCategoryPreviousLimit(Category category, Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ContentValues values = new ContentValues();
        values.put("category_id", category.get_id());
        values.put("limitt", category.get_limit());
        values.put("month", sdf.format(date));

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
            String []dateString = c.getString(c.getColumnIndex("date")).split("-");
            Date date = new Date(Integer.parseInt(dateString[0]) - 1900, Integer.parseInt(dateString[1]) -1, Integer.parseInt(dateString[2]));
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

        String []dateStringCreation = c.getString(c.getColumnIndex("creation_date")).split("-");
        Date creationDate;
        if(dateStringCreation.length == 3) {
            creationDate = new Date(Integer.parseInt(dateStringCreation[0]) - 1900, Integer.parseInt(dateStringCreation[1]) - 1, Integer.parseInt(dateStringCreation[2]));
        }else{
            creationDate = null;
        }

        String []dateStringDestroyed = c.getString(c.getColumnIndex("destroyed_date")).split("-");
        Date destroyedDate;
        if(dateStringDestroyed.length == 3) {
            destroyedDate = new Date(Integer.parseInt(dateStringDestroyed[0]) - 1900, Integer.parseInt(dateStringDestroyed[1]) - 1, Integer.parseInt(dateStringDestroyed[2]));
        }else{
            destroyedDate = null;
        }

        Category category = new Category(_id,limit,name,pitureName,color,creationDate,destroyedDate);
        db.close();
        return category;
    }


    public void deleteExpense(Expense expense) {

        final int id = expense.get_id();
        db.delete(TABLE_EXPENSE, "_id=?", new String[]{Integer.toString(id)});

    }

    public int getTotalSpentMoney(Date date) {

        return 1;
    }


    /**
     * A method to add a new category to the database.
     */
    public void addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("name", category.get_name());
        values.put("limitt", category.get_limit());
        values.put("picture_name",category.get_pictureName());
        values.put("color",category.get_color());
        values.put("creation_date",category.getCreationDate().toString());
        values.put("destroyed_date", category.getDestroyedDate().toString());

        db.insert(TABLE_CATEGORY,null,values);
        db.close();
    }


}
