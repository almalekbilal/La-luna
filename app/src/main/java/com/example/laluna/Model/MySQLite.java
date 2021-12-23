package com.example.laluna.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "laluna.db";
    public static final String TABLE_CATEGORY = "categories";
    public static final String TABLE_EXPENSE = "expenses";
    public static final String TABLE_LIMITS = "limits";





    public MySQLite(@Nullable Context context, @Nullable String name,
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



}
