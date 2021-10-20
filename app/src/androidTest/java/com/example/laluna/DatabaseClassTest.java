
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
 * A (unit)test class for testing the database.
 *
 * @author Ali Alkhaled
 *
 * @author Deaa Khankan
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class DatabaseClassTest {

    private Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private DBHandler db = new DBHandler(appContext);


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
    public void addCategoryGeneralTest() {
        Category c1 = db.addCategory("food", 100, 0, null, new Date(121, 01, 01));
        db.addCategory("car", 500, 0, "red", new Date(120, 01, 01));

        List<Category> categories = db.getCategories(new Date(121, 01, 03));
        assertEquals(categories.size(), 2);

        assertEquals(true,categories.contains(c1));
    }

    @Test
    public void getCategoriesTest() {

        db.addCategory("food", 100, 0, "blue", new Date(120, 05, 01));

        db.addCategory("car", 500, 0, "red", new Date(120, 05, 01));

        db.addCategory("clothes", 100, 0, "blue", new Date(120, 04, 01));

        db.addCategory("other", 500, 0, "red", new Date(120, 05, 01));

        List<Category> categories = db.getCategories(new Date(120, 7, 03));
        assertEquals(categories.size(), 4);
    }


    @Test
    public void deactivateCategoryGeneralTest() {

        Category category1 = db.addCategory("food", 100, 0, "blue", new Date(121, 01, 01));

        Category category2 = db.addCategory("car", 500, 0, "green", new Date(120, 01, 01));

        db.deactivateCategory(category1, new Date(121, 02, 01));

        db.deactivateCategory(category2, new Date(121, 02, 01));

        List<Category> categories2 = db.getCategories(new Date(120, 9, 01));

        assertEquals(1, categories2.size());

    }

    @Test
    public void deactivateCategoryInTheSameMonthTest() {

        Category category1 = db.addCategory("cat1", 100, 0, "blue", new Date(121, 03, 27));

        Category category2 = db.addCategory("cat2", 400, 0, "green", new Date(121, 04, 15));

        Category category3 = db.addCategory("cat3", 1050, 0, "blue", new Date(121, 05, 9));

        Category category4 = db.addCategory("cat4", 850, 0, "green", new Date(121, 06, 22));

        List <Category> categories = db.getCategories(new Date(121, 7, 1));

        boolean hasBeenAdded = categories.contains(category1) && categories.contains(category2) &&
                categories.contains(category3) && categories.contains(category4);

        db.deactivateCategory(category1, new Date(121, 07, 1));

        db.deactivateCategory(category2, new Date(121, 07, 2));

        db.deactivateCategory(category3, new Date(121, 07, 3));

        db.deactivateCategory(category4, new Date(121, 07, 4));

        categories = db.getCategories(new Date(121, 7, 5));

        boolean hasBeenDeactivated = hasBeenAdded && categories.size()==0;

        assertTrue(hasBeenDeactivated);


    }


    @Test
    public void deactivateCategoryInTheSameDayTest() {

        Category category1 = db.addCategory("cat1", 100, 0, "blue", new Date(121, 03, 27));

        Category category2 = db.addCategory("cat2", 400, 0, "green", new Date(121, 04, 15));

        Category category3 = db.addCategory("cat3", 1050, 0, "blue", new Date(121, 05, 9));

        db.deactivateCategory(category1, new Date(121, 06, 4));

        db.deactivateCategory(category2, new Date(121, 06, 5));

        db.deactivateCategory(category3, new Date(121, 06, 4));

        List<Category> testCategoryList = db.getCategories(new Date(121, 06, 4));

        assertEquals(3, testCategoryList.size());


    }


    @Test
    public void deactivateCategoryMoreConditionsTest() {

        Category category1 = db.addCategory("food", 100, 0, "blue", new Date(121, 01, 01)); // does not count

        Category category2 = db.addCategory("car", 500, 0, "green", new Date(120, 01, 01));

        Category category3 = db.addCategory("truck", 1000, 0, "red", new Date(120, 05, 01));

        Category category4 = db.addCategory("pizza", 52, 0, "black", new Date(120, 10, 01)); // does not count

        Category category5 = db.addCategory("Other", 1000, 0, "white", new Date(120, 05, 01));

        Category category6 = db.addCategory("Drinks", 400, 0, "red", new Date(120, 12, 01)); // does not count

        Category category7 = db.addCategory("Uni", 350, 0, "green", new Date(120, 9, 02)); // does not count

        Category category8 = db.addCategory("Clothes", 900, 0, "blue", new Date(120, 8, 01));

        Category category9 = db.addCategory("House", 1200, 0, "red", new Date(120, 07, 01));

        Category category10 = db.addCategory("Night out", 1000, 0, "black", new Date(120, 01, 01));


        db.deactivateCategory(category1, new Date(121, 02, 01)); // will be counted

        db.deactivateCategory(category2, new Date(121, 02, 01)); // will be counted

        db.deactivateCategory(category5, new Date(120, 10, 30)); // will be counted

        db.deactivateCategory(category3, new Date(120, 9, 30)); // will be counted

        db.deactivateCategory(category9, new Date(120, 8, 30)); //will not be counted

        db.deactivateCategory(category10, new Date(120, 8, 01)); //will not be counted

        List<Category> categories2 = db.getCategories(new Date(120, 9, 01));

        assertEquals(categories2.size(), 5);

    }

    @Test
    public void addExpenseTest() {
        Category category1 = db.addCategory("food", 100, 0, "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, 0, "red", new Date(120, 01, 01));

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
        db.addExpense("swish",500,new Date(119,01,04), category1); //does not count
        db.addExpense("swish",500,new Date(121,01,04), category1); //does not count
        db.addExpense("swish",500,new Date(120,02,04), category1); //does not count
        db.addExpense("swish",500,new Date(120,03,04), category1); //does not count
        db.addExpense("swish",500,new Date(121,01,04), category1); //does not count
        Expense expense =  db.addExpense("swish",500,new Date(120,01,04), category2);


        List<Expense> expenses = db.getExpenses(0, 6);

        assertEquals(6, expenses.size());
        assertEquals(true, expenses.contains(expense));

    }

    @Test
    public void deleteExpenseGeneralTest() {

        Category category1 = db.addCategory("food", 500, 0, "blue", new Date(121, 01, 01));

        Expense expense = db.addExpense("Burger", 120, new Date(121, 01, 29), category1);

        List<Expense> expenses = db.getExpenses(0, 10);

        boolean hasBeenAdded = expenses.contains(expense);

        db.deleteExpense(expense);

        expenses = db.getExpenses(0, 10);

        boolean hasBeenDeleted = hasBeenAdded && !expenses.contains(expense);

        assertTrue(hasBeenDeleted);

    }


    @Test
    public void deleteExpenseMoreConditionsTest() {

        Category category1 = db.addCategory("food", 100, 0, "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, 0, "red", new Date(120, 01, 01));

        Expense expense1 = db.addExpense("ex1",100,new Date(121,01,01), category1);

        Expense expense2 =  db.addExpense("ex2",150,new Date(121,01,01), category1);

        Expense expense3 =  db.addExpense("ex3",280,new Date(120,01,01), category2);

        Expense expense4 =  db.addExpense("ex4",500,new Date(120,01,01), category2);

        Expense expense5 =  db.addExpense("ex5",130,new Date(120,01,01), category2);

        Expense expense6 =  db.addExpense("ex6",500,new Date(121,01,01), category1);



        db.deleteExpense(expense1);
        db.deleteExpense(expense2);
        db.deleteExpense(expense3);
        db.deleteExpense(expense4);
        db.deleteExpense(expense5);


        List<Expense> expenses1 = db.getCategoryExpense(category1.get_id());

        List<Expense> expenses2 = db.getCategoryExpense(category2.get_id());

        boolean result = expenses1.size()==1 && expenses2.size()==0;

        assertTrue(result);


    }


    @Test
    public void updateExpensesTest(){

        Category category1 = db.addCategory("food", 100, 0, "blue", new Date(121, 01, 01));
        Category category2 = db.addCategory("car", 500, 0, "red", new Date(120, 01, 01));



        Expense expense = db.addExpense("swish",500,new Date(120,01,01), category1);
        Expense expense1 = db.addExpense("swish",500,new Date(120,01,01), category2);


        expense1.set_value(400);
        db.updateExpense(expense1);

        List<Expense> expenses = db.getExpenses(0,10);
        assertEquals(expenses.get(0).get_value(),400);

    }

    @Test
    public void getTotalMoneySpentGeneralTest(){

        Category category = db.addCategory("cat", 1500, 5, null, new Date(121, 4, 1));

        db.addExpense("test", 500, new Date(121, 4, 1), category);

        int totalMoneySpent = db.getTotalMoneySpent(new Date(121, 4, 1));

        assertEquals(500, totalMoneySpent);
    }


    @Test
    public void getTotalMoneySpentMoreConditionsTest(){

        Category c1 = db.addCategory("food",100,0, "blue",new Date(120,5,1));
        Category c2 = db.addCategory("car",500,0, "red",new Date(2020,05,01));


        db.addExpense("KFC", 45, new Date(120,5,1), c1);
        db.addExpense("Book", 150, new Date(120, 5, 13), c2);
        db.addExpense("Internet", 120, new Date(120, 5, 19), c2);
        db.addExpense("Ice cream", 19, new Date(121, 5, 4), c2); // does not count
        db.addExpense("Book", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Calculus", 100, new Date(120, 5, 6), c2);
        db.addExpense("Book3", 98, new Date(120, 5, 23), c2);
        db.addExpense("Book4", 98, new Date(120, 1, 9), c2); //does not count
        db.addExpense("Book5", 20, new Date(120, 5, 6), c2);
        db.addExpense("Book6", 30, new Date(120, 5, 29), c2);
        db.addExpense("Book7", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book8", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book9", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book10", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book11", 15, new Date(120, 5, 22), c2); //does not count
        db.addExpense("Book12", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book13", 1000, new Date(120, 5, 30), c2);
        db.addExpense("Book14", 20, new Date(120, 5, 9), c2);
        db.addExpense("Book15", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book16", 98, new Date(120, 1, 6), c2); //does not count
        db.addExpense("Book17", 69, new Date(120, 5, 30), c2);

        Expense expense = db.addExpense("Clothes", 90, new Date(120, 5, 6), c2); // does not count
        db.deleteExpense(expense);


        int totalMoney = db.getTotalMoneySpent(new Date(120,5,1));
        assertEquals(1667,totalMoney);

    }

    @Test
    public void getTotalMoneySpentByCategoryTest() {
        Category c1 = db.addCategory("food",100,0, "blue",new Date(121,5,1));
        Category c2 = db.addCategory("car",100,0, "blue",new Date(121,5,1));

        db.addExpense("Book", 21, new Date(), c1);
        db.addExpense("Burger", 22, new Date(121, 2, 4), c1); //does not count
        db.addExpense("Notebook", 9, new Date(), c1);
        db.addExpense("Pizza", 80, new Date(), c1);
        db.addExpense("Calculus", 9, new Date(121, 1, 29), c1);//does not count
        db.addExpense("Pen", 12, new Date(120, 6, 29), c1);//does not count
        db.addExpense("Pen", 12, new Date(121, 5, 15), c2);//does not count
        db.addExpense("Oil", 20, new Date(121, 5, 10), c2);//does not count
        db.addExpense("Window", 200, new Date(121, 5, 10), c2);//does not count
        db.addExpense("Water", 12, new Date(), c1);
        db.addExpense("Cola", 25, new Date(), c1);
        db.addExpense("Cake", 40, new Date(), c1);
        db.addExpense("Burger", 12, new Date(121, 5, 15), c2);//does not count
        db.addExpense("Pizza", 12, new Date(121, 5, 15), c2);//does not count

        Expense e = db.addExpense("rer", 30, new Date(121, 5, 3), c1);
        db.deleteExpense(e);



        int totalMoney = db.getTotalSpentByCategory(new Date(), c1);

        assertEquals(187, totalMoney);
    }

    @Test
    public void getTotalBudgetGeneralTest(){

        db.addCategory("Food", 1500, 0, null, new Date(121, 5,1));

        db.setCategoriesPreviousLimits(new Date(121, 5, 4));

        assertEquals(db.getTotalBudget(new Date(121, 5, 4)), 1500);

    }


    @Test
    public void getTotalBudgetMoreConditionsTest(){

        Category c1 = db.addCategory("Mat",2000,0,null,new Date(121,5,1));

        Category c2 = db.addCategory("Kaffe",150,0,null,new Date(121,5,1));

        Category c3 = db.addCategory("Kläder",500,0,null,new Date(121,5,1));

        Category c4 = db.addCategory("Snacks",200,0,null,new Date(121,5,1));


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
    public void getCategoryExpensesGeneralTest(){

        Category c1 = db.addCategory("Cat1", 2000, 0, null, new Date(121, 01, 01));

        db.addExpense("Expense1", 700, new Date(121, 05, 1), c1);

        List<Expense> expenses = db.getCategoryExpense(c1.get_id());

        assertEquals(1, expenses.size());
    }

    @Test
    public void getCategoryExpensesMoreConditionsTest() {

        Category c1 = db.addCategory("Food",3000,0,null,new Date());

        Category c2 = db.addCategory("entertainment",700,0,null,new Date());

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

        List<Expense> expenses = db.getCategoryExpense(c1.get_id());

        assertEquals(4,expenses.size());

    }

    @Test
    public void getCategoryLimitGeneralTest(){

        //null pointer exception
        //out of index exception if not giving exactly the same date.

        Category c1 = db.addCategory("Food",3000,0,null, new Date(121, 5, 1));

        db.setCategoriesPreviousLimits(new Date(121,5,1));

        int limit = db.getCategoryLimit(new Date(121, 5, 1), c1); ///THE SAME DATE!!

        assertEquals(3000, limit);
    }

    /*@Test
    public void ViewThings(){
        Category c1 = db.addCategory("Food",3000,R.drawable.categorycar,null,new Date(121,9,2));
        Category c2 = db.addCategory("entertainment",2000,R.drawable.food,null,new Date(121,9,2));

        Category c18 = db.addCategory("Car",1000,R.drawable.food,null,new Date(121,8,1));
        Category c28 = db.addCategory("Drinks",3000,R.drawable.ic_home_black_24dp,null,new Date(121,8,1));


        db.addExpense("Pizza", 80,new Date(),c1);
        db.addExpense("KFC", 20,new Date(),c1);
        db.addExpense("Pizza", 100,new Date(),c1);
        db.addExpense("Cheese", 300,new Date(),c1);
        db.addExpense("Tacos", 200,new Date(),c1);

        //does not count


        db.addExpense("Disco", 200,new Date(),c2);
        db.addExpense("zip line", 500,new Date(),c2);

        db.addExpense("Window", 80,new Date(121,8,2),c18);
        db.addExpense("Gear", 20,new Date(121,8,2),c18);
        db.addExpense("Pip", 100,new Date(121,8,2),c18);
        db.addExpense("Oil", 300,new Date(121,8,2),c18);
        db.addExpense("Oil", 200,new Date(121,8,2),c18);

        //does not count

        db.addExpense("Vodka", 80,new Date(121,8,2),c28);
        db.addExpense("Bear", 500,new Date(121,8,2),c28);
        db.addExpense("Coca cola", 200,new Date(121,8,2),c28);
        db.addExpense("Pepsi", 500,new Date(121,8,2),c28);

        db.addExpense("Fanta", 700,new Date(),c28);
        db.addExpense("whiskey", 300,new Date(),c18);

        db.addExpense("Oil", 80,new Date(121,8,2),c18);
        db.addExpense("Motor", 80,new Date(121,8,2),c18);
        db.addExpense("CS go", 80,new Date(),c2);
        db.addExpense("Liseberg", 500,new Date(),c2);
        db.addExpense("Burger", 80,new Date(),c1);
        db.addExpense("Fish", 80,new Date(),c1);

        db.setCategoriesPreviousLimits(new Date(121,8,1));

        assertEquals(3000,3000);
    }

     */
}





