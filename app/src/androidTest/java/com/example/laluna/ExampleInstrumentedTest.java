
package com.example.laluna;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;
import com.example.laluna.Model.Expense;


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

    DBHandler db = new DBHandler(appContext);


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
        Category c1 = db.addCategory("food", 100, null, null, new Date(121, 01, 01));
        db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));

        List<Category> categories = db.getCategories(new Date(121, 01, 03));
        assertEquals(categories.size(), 2);

        assertEquals(true,categories.contains(c1));
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
        List<Category> categories2 = db.getCategories(new Date(120, 9, 01));

        assertEquals(categories2.size(), 1);

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
        db.addExpense("swish",500,new Date(120,01,04), category1);
        db.addExpense("swish",500,new Date(120,01,04), category2);
        db.addExpense("swish",500,new Date(120,01,04), category1);
        db.addExpense("swish",500,new Date(120,01,04), category2);
        db.addExpense("swish",500,new Date(120,01,04), category1);
        Expense expense =  db.addExpense("swish",500,new Date(120,01,04), category2);


        List<Expense> expenses = db.getExpenses(0, 6);

        assertEquals(6, expenses.size());
        assertEquals(true, expenses.contains(expense));

    }


    @Test
    public void deleteExpenseTest() {
        Category category1 = db.addCategory("food", 100, "pic1", "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));

        Expense expense = db.addExpense("swish",500,new Date(120,01,01), category1);
        Expense expense1 =  db.addExpense("swish",500,new Date(120,01,01), category2);


        db.deleteExpense(expense);


        List<Expense> expenses = db.getExpenses(0, 10);

        assertEquals(1, expenses.size());
        assertEquals(false ,expenses.contains(expense) );

    }


    @Test
    public void updateExpensesTest(){
        Category category1 = db.addCategory("food", 100, "pic1", "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, "pic2", "red", new Date(120, 01, 01));



        Expense expense = db.addExpense("swish",500,new Date(120,01,01), category1);
        Expense expense1 = db.addExpense("swish",500,new Date(120,01,01), category2);


        expense1.set_value(400);
        db.updateExpense(expense1);

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
        Category c1 = db.addCategory("food",100,"pic1", "blue",new Date(121,5,1));
        Category c2 = db.addCategory("car",100,"pic1", "blue",new Date(121,5,1));

        db.addExpense("Book", 21, new Date(), c1);
        db.addExpense("Burger", 22, new Date(121, 2, 4), c1); //does not count
        db.addExpense("Notebook", 9, new Date(), c1);
        db.addExpense("Pizza", 80, new Date(), c1);
        db.addExpense("Calculus", 9, new Date(121, 1, 29), c1);//does not count
        db.addExpense("Pen", 12, new Date(120, 6, 29), c1);//does not count
        db.addExpense("Pen", 12, new Date(121, 5, 15), c2);//does not count
        db.addExpense("Oil", 20, new Date(121, 5, 10), c2);//does not count
        db.addExpense("Window", 200, new Date(121, 5, 10), c2);//does not count

        Expense e = db.addExpense("rer", 30, new Date(121, 5, 3), c1);
        db.deleteExpense(e);



        int totalMoney = db.getTotalSpentByCategory(new Date(), c1);

        assertEquals(totalMoney, 110);
    }

    @Test
    public void getTotalBudgetTest(){

        Category c1 = db.addCategory("Mat",2000,null,null,new Date(121,5,1));

        Category c2 = db.addCategory("Kaffe",150,null,null,new Date(121,5,1));

        Category c3 = db.addCategory("Kläder",500,null,null,new Date(121,5,1));

        Category c4 = db.addCategory("Snacks",200,null,null,new Date(121,5,1));


        db.setCategoriesPreviousLimits(new Date(121,5,1));

        c1.set_limit(1000);
        c2.set_limit(200);
        c3.set_limit(600);
        c4.set_limit(150);

        db.updateCategory(c1);
        db.updateCategory(c2);
        db.updateCategory(c3);
        db.updateCategory(c4);

        assertEquals(2850,db.getTotalBudget(new Date(121,5,1)));
        assertEquals(1950,db.getTotalBudget(new Date(121,9,1)));

    }


    @Test
    public void getCategoryExpensesTest() {

        Category c1 = db.addCategory("Food",3000,null,null,null);
        Category c2 = db.addCategory("entertainment",700,null,null,null);

        //will count
        db.addExpense("Burger", 80,new Date(121,5,1),c1);
        db.addExpense("Fish", 80,new Date(121,5,5),c1);
        db.addExpense("Pizza", 80,new Date(121,5,15),c1);
        db.addExpense("Coca cola", 20,new Date(121,5,22),c1);

        //does not count

        db.addExpense("Counter", 80,new Date(121,5,1),c2);
        db.addExpense("Liseberg", 500,new Date(121,5,5),c2);
        db.addExpense("Disco", 200,new Date(121,5,15),c2);
        db.addExpense("prostitution", 500,new Date(121,5,22),c2);

        List<Expense> expenses = db.getCategoryExpense(c1);

        assertEquals(4,expenses.size());

    }

    @Test
    public void getCategoryLimitTest(){
        Category c1 = db.addCategory("Food",3000,null,null,null);

        db.setCategoriesPreviousLimits(new Date(121,5,5));

        c1.set_limit(3500);
        db.updateCategory(c1);

        db.setCategoriesPreviousLimits(new Date(121,6,7));

        assertEquals(3000,db.getCategoryLimit(new Date(121,5,1),c1));

        assertEquals(3500,db.getCategoryLimit(new Date(121,6,1),c1));
    }

    @Test
    public void ViewThings(){
        Category c1 = db.addCategory("Food",3000,null,null,null);
        Category c2 = db.addCategory("entertainment",700,null,null,null);
        Category c3 = db.addCategory("Car",500,null,null,null);

        db.addExpense("Burger", 80,new Date(),c1);
        db.addExpense("Fish", 80,new Date(),c1);
        db.addExpense("Pizza", 80,new Date(),c1);
        db.addExpense("Coca cola", 20,new Date(),c1);

        //does not count

        db.addExpense("Counter", 80,new Date(),c2);
        db.addExpense("Liseberg", 500,new Date(),c2);
        db.addExpense("Disco", 200,new Date(),c2);
        db.addExpense("prostitution", 500,new Date(),c2);


        db.addExpense("Diesel",80,new Date(),c3);

        assertEquals(3000,3000);
    }
}





/*
    //not DONE!!!!!!!
    @Test
    public void getTotalBudgetTest(){
        db.addCategoryDB("food",100,"pic1", "blue",new Date(120,05,01));
        db.addCategoryDB("car",500,"pic2", "red",new Date(120,05,01));
        db.addCategoryDB("clothes",100,"pic3", "blue",new Date(120,04,01));
        db.addCategoryDB("other",500,"pic4", "red",new Date(120,05,01));

        int totalBudget= db.getTotalBudgetDB(new Date(120,05,01));
        assertEquals(totalBudget,1100);

    }
}
*/
