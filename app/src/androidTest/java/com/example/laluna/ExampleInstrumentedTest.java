
package com.example.laluna;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.laluna.DatabaseClasses.Category;
import com.example.laluna.DatabaseClasses.DBHandler;
import com.example.laluna.DatabaseClasses.Expense;


import org.junit.After;
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
public class ExampleInstrumentedTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    DBHandler db = new DBHandler(appContext, null, null, 1);


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
    public void addCategoryTest() {
        db.addCategory("food", 100, "pic1", "blue", new Date(121, 01, 01));
        db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));

        List<Category> categories = db.getCategories(new Date(121, 01, 03));
        assertEquals(categories.size(), 2);

    }

    @Test
    public void getCategoriesTest() {
        db.addCategory("food", 100, "pic1", "blue", new Date(120, 05, 01));
        db.addCategory("car", 500, "pic2", "red", new Date(120, 05, 01));
        db.addCategory("clothes", 100, "pic3", "blue", new Date(120, 04, 01));
        db.addCategory("other", 500, "pic4", "red", new Date(120, 05, 01));

        List<Category> categories = db.getCategories(new Date(120, 7, 03));
        assertEquals(categories.size(), 4);
    }


    @Test
    public void deactiveCategoryTest() {
        Category category1 = db.addCategory("food", 100, "pic1", "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));

        db.deactivateCategory(category1, new Date(121, 02, 01));
        List<Category> categories = db.getCategories(new Date(121, 03, 01));
        assertEquals(categories.size(), 1);

        db.deactivateCategory(category2, new Date(121, 02, 01));
        List<Category> categories2 = db.getCategories(new Date(121, 01, 01));

        assertEquals(categories.size(), 1);

    }

    @Test
    public void addExpenseTest() {
        Category category1 = db.addCategory("food", 100, "pic1", "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));

        db.addExpense("swish",500,new Date(120,01,01), category1);
        db.addExpense("swish",500,new Date(120,01,01), category2);
        db.addExpense("swish",500,new Date(120,01,01), category1);
        db.addExpense("swish",500,new Date(120,01,01), category2);
        db.addExpense("swish",500,new Date(120,01,01), category1);
        db.addExpense("swish",500,new Date(120,01,01), category2);
        db.addExpense("swish",500,new Date(120,01,01), category1);
        db.addExpense("swish",500,new Date(120,01,01), category2);
        db.addExpense("swish",500,new Date(120,01,01), category1);
        db.addExpense("swish",500,new Date(120,01,01), category2);
        db.addExpense("swish",500,new Date(120,01,01), category1);
        db.addExpense("swish",500,new Date(120,01,01), category2);


        List<Expense> expenses = db.getExpenses(0, 12);

        assertEquals(expenses.size(), 12);

    }


    @Test
    public void deleteExpenseTest() {
        Category category1 = db.addCategory("food", 100, "pic1", "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));

        Expense expense = db.addExpense("swish",500,new Date(120,01,01), category1);
        Expense expense1 =  db.addExpense("swish",500,new Date(120,01,01), category2);


        db.deleteExpense(expense);


        List<Expense> expenses = db.getExpenses(0, 10);

        assertEquals(expenses.size(), 1);

    }

    @Test
    public void updateExpensesTest(){
        Category category1 = db.addCategory("food", 100, "pic1", "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));



        Expense expense = db.addExpense("swish",500,new Date(120,01,01), category1);
        Expense expense1 = db.addExpense("swish",500,new Date(120,01,01), category2);


        expense.set_value(400);
        db.updateExpense(expense);

        List<Expense> expenses = db.getExpenses(0,10);
        assertEquals(expenses.get(0).get_value(),400);

    }

}




/*
    //not DONE!!!!!!!
    @Test
    public void getTotalBudgetTest(){
        db.addCategory("food",100,"pic1", "blue",new Date(120,05,01));
        db.addCategory("car",500,"pic2", "red",new Date(120,05,01));
        db.addCategory("clothes",100,"pic3", "blue",new Date(120,04,01));
        db.addCategory("other",500,"pic4", "red",new Date(120,05,01));

        int totalBudget= db.getTotalBudget(new Date(120,05,01));
        assertEquals(totalBudget,1100);

    }
}
*/
