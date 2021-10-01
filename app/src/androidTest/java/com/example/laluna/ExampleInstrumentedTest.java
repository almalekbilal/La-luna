
package com.example.laluna;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.laluna.DatabaseClasses.Category;
import com.example.laluna.DatabaseClasses.SqliteHandler;
import com.example.laluna.DatabaseClasses.Expense;


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

    SqliteHandler db = new SqliteHandler(appContext, null, null, 1);


    @Before
    public void destroyDataBase() {
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

    @Test
    public void getTotalMoneySpentTest(){
        Category c1 = db.addCategory("food",100,"pic1", "blue",new Date(120,5,1));
        Category c2 = db.addCategory("car",500,"pic2", "red",new Date(2020,05,01));


        db.addExpense("KFC", 45, new Date(120,5,1), c1);
        db.addExpense("Book", 150, new Date(120, 5, 6), c2);
        db.addExpense("Internet", 120, new Date(120, 5, 6), c2);
        db.addExpense("Ice cream", 19, new Date(121, 5, 6), c2); // does not count
        db.addExpense("Book", 98, new Date(120, 1, 6), c2); //does not count
        Expense expense = db.addExpense("Clothes", 90, new Date(120, 5, 6), c2); // does not count
        db.deleteExpense(expense);


        int totalMoney = db.getTotalMoneySpent(new Date(2020,5,1));
        assertEquals(totalMoney,315);

    }

    @Test
    public void getTotalMoneySpentByCategoryTest() {
        Category c = db.addCategory("food",100,"pic1", "blue",new Date(121,5,1));

        db.addExpense("Book", 21, new Date(121, 5, 4), c);
        db.addExpense("Burger", 22, new Date(121, 2, 4), c); //does not count
        db.addExpense("Notebook", 9, new Date(121, 5, 29), c);
        db.addExpense("Pizza", 80, new Date(121, 5, 18), c);
        db.addExpense("Calculus", 9, new Date(121, 1, 29), c);//does not count
        db.addExpense("Pen", 12, new Date(120, 6, 29), c);//does not count
        Expense e = db.addExpense("rer", 30, new Date(121, 5, 3), c);
        db.deleteExpense(e);



        int totalMoney = db.getTotalSpentByCategory(new Date(2021, 5, 1), c);

        assertEquals(totalMoney, 110);
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

        int budget = db.getTotalBudget(new Date(121,5,1));

        assertEquals(2850,budget);

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
