package com.example.laluna;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.laluna.DatabaseClasses.Category;
import com.example.laluna.DatabaseClasses.SqliteHandler;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BilalTesting {


    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    SqliteHandler db = new SqliteHandler(appContext,null,null,1);





    @Before
    public void destoryDataBase() {
        appContext.deleteDatabase("laluna.db");
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.


        assertEquals("com.example.laluna", appContext.getPackageName());
    }

    @Test
    public void addCategoryTest(){
        //  db.addCategory(new Category(8,2000, "Fuck junit", null,null,null,null));

        List<Category> categories = db.getCategories(new Date());

        assertEquals(categories.size(), 6);


    }

    @Test
    public void getTotalBudgetTest(){

        db.addCategory("Mat",2000,null,null,new Date(121,5,1));

        db.addCategory("Kaffe",150,null,null,new Date(121,5,1));

        db.addCategory("Kläder",500,null,null,new Date(121,5,1));

        db.addCategory("Snacks",200,null,null,new Date(121,5,1));

        db.addCategory("Mat",1000,null,null,new Date(121,6,1));

        db.addCategory("Kaffe",150,null,null,new Date(121,6,1));

        db.addCategory("Kläder",500,null,null,new Date(121,6,1));

        db.addCategory("Snacks",200,null,null,new Date(121,6,1));

        db.setCategoriesPreviousLimits(new Date(121,5,1));

        db.setCategoriesPreviousLimits(new Date(121,6,1));

        int budget = db.getTotalBudget(new Date(121,6,1));

        assertEquals(1850,budget);

    }


}
