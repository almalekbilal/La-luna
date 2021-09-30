package com.example.laluna;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.laluna.DatabaseClasses.Category;
import com.example.laluna.DatabaseClasses.DBHandler;
import com.example.laluna.DatabaseClasses.Expense;


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
public class ExampleInstrumentedTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    DBHandler db = new DBHandler(appContext,null,null,1);



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

}
