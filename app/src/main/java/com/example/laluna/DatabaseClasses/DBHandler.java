package com.example.laluna.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
        SQLiteDatabase db = getWritableDatabase();
        List <Expense> expensesList = new ArrayList<Expense>();
        int categoryID=category.get_id();
        String query = "SELECT *  FROM " + TABLE_EXPENSE + "WHERE category_id =" + categoryID;

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int expenseValue = cursor.getInt(
                        cursor.getColumnIndex("value") );

            String []dateString = cursor.getString
                    (cursor.getColumnIndex("date")).split("-");

            Date expenseDate = new Date(Integer.parseInt(dateString[0]) - 1900
                    , Integer.parseInt(dateString[1]) -1,
                    Integer.parseInt(dateString[2]));

                expensesList.add(new Expense(expenseValue, expenseDate, category));
                cursor.moveToNext();
        }
        db.close();
        return expensesList;
    }

    public void addExpense(Expense expense){
        ContentValues values = new ContentValues();
        values.put("name", expense.get_name());
        values.put("value", expense.get_value());
        values.put("date",Date expenseDate);

    }

    public stringToDate(String){


    }

}
