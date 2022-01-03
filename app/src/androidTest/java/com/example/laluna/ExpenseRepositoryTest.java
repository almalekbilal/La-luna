package com.example.laluna;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.Category;
import com.example.laluna.Model.Expense;
import com.example.laluna.Model.repository.CategoryRepository;
import com.example.laluna.Model.repository.ExpenseRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class ExpenseRepositoryTest {

    private Context appContext;

    private ExpenseRepository expenseRepository;
    
    private CategoryRepository categoryRepository;


    @Before
    public void destroyDataBase() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        expenseRepository = ExpenseRepository.getInstance(appContext);
        categoryRepository = CategoryRepository.getInstance(appContext);
        appContext.deleteDatabase("laluna.db");

    }

    @After
    public void destroyDataBase2() {
        appContext.deleteDatabase("laluna.db");
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        
        assertEquals("com.example.laluna", appContext.getPackageName());
    }
    
    
    @Test
    public void getExpenseTest(){
        Category c1 = categoryRepository.addCategory("food",100,0, "blue",new Date(121,5,1));
        Category c2 = categoryRepository.addCategory("car",100,0, "blue",new Date(121,5,1));


        expenseRepository.addExpense("Book", 21, new Date(), c1);
        expenseRepository.addExpense("Notebook", 9, new Date(), c1);
        expenseRepository.addExpense("Pizza", 80, new Date(), c1);
        expenseRepository.addExpense("Water", 12, new Date(), c1);
        expenseRepository.addExpense("Cola", 25, new Date(), c1);
        expenseRepository.addExpense("Cake", 40, new Date(), c1);

        expenseRepository.addExpense("Burger", 22, new Date(121, 6, 4), c1);
        expenseRepository.addExpense("Calculus", 9, new Date(121, 6, 15), c1);
        expenseRepository.addExpense("Pen", 12, new Date(121, 6, 20), c1);
        expenseRepository.addExpense("Pen", 12, new Date(121, 7, 2), c2);
        expenseRepository.addExpense("Oil", 20, new Date(121, 7, 5), c2);
        expenseRepository.addExpense("Window", 200, new Date(121, 8, 2), c2);

        expenseRepository.addExpense("Burger", 12, new Date(121, 8, 7), c2);
        expenseRepository.addExpense("Pizza", 12, new Date(121, 8, 10), c2);

        expenseRepository.addExpense("Cola", 25, new Date(), c1);
        expenseRepository.addExpense("Cake", 40, new Date(), c1);


        List<Expense> expenses = expenseRepository.getExpenses(0,2);

        int totalSpentMoney = Arithmetic.calculateTotalMoneySpent(expenses);
        assertEquals(65, totalSpentMoney);
    }

    @Test
    public void getTotalMoneySpendTest(){

        Category c1 = categoryRepository.addCategory("food",100,0, "blue",new Date(121,5,1));

        expenseRepository.addExpense("Cola", 25, new Date(), c1);
        expenseRepository.addExpense("Cake", 40, new Date(), c1);

        expenseRepository.addExpense("Burger", 22, new Date(121, 6, 4), c1);
        expenseRepository.addExpense("Calculus", 9, new Date(121, 6, 15), c1);
        expenseRepository.addExpense("Pen", 12, new Date(121, 6, 20), c1);

        int totalMoneySpent = expenseRepository.getTotalMoneySpend(new Date(121,06,25));

        assertEquals(43,totalMoneySpent);
    }

    @Test
    public void getExpenseByDateTest(){
        Category c1 = categoryRepository.addCategory("food",100,0, "blue",new Date(121,5,1));
        Category c2 = categoryRepository.addCategory("car",100,0, "blue",new Date(121,5,1));


        expenseRepository.addExpense("Book", 21, new Date(), c1);
        expenseRepository.addExpense("Notebook", 9, new Date(), c1);
        expenseRepository.addExpense("Pizza", 80, new Date(), c1);

        expenseRepository.addExpense("Burger", 22, new Date(121, 6, 4), c1);
        expenseRepository.addExpense("Calculus", 9, new Date(121, 6, 15), c1);
        expenseRepository.addExpense("Pen", 12, new Date(121, 7, 2), c2);

        List<Expense> expenses = expenseRepository.getExpensesByDate(new Date(121,06,20));

        assertEquals(2,expenses.size());

    }

    @Test
    public void getExpensesByStartAndEndTest(){
        Category c1 = categoryRepository.addCategory("food",100,0, "blue",new Date(121,5,1));

        expenseRepository.addExpense("Pizza", 80, new Date(), c1);

        expenseRepository.addExpense("Burger", 22, new Date(121, 6, 4), c1);
        expenseRepository.addExpense("Calculus", 9, new Date(121, 6, 15), c1);

        List<Expense> expenses = expenseRepository.getExpensesByStartAndEnd(new Date(121,5,1),new Date(121,6,20));

        assertEquals(2,expenses.size());

    }


    @Test
    public void updateExpenseTest(){
        Category food = categoryRepository.addCategory("food", 100, 0, "blue", new Date());
        Category clothes = categoryRepository.addCategory("clothes", 100, 0, "blue", new Date());

        Expense pizza = expenseRepository.addExpense("Pizza", 80, new Date(), food);
        Expense hm= expenseRepository.addExpense("H&M", 80, new Date(), clothes);

        pizza.set_value(75);
        hm.set_value(90);

        expenseRepository.updateExpense(pizza);
        expenseRepository.updateExpense(hm);
        int totalSpentMoneyAfterUpdating = expenseRepository.getTotalMoneySpend(new Date());

        assertEquals(165,totalSpentMoneyAfterUpdating);
    }

    @Test
    public void deleteExpenseTest(){
        Category food = categoryRepository.addCategory("food", 100, 0, "blue", new Date());
        Category clothes = categoryRepository.addCategory("clothes", 100, 0, "blue", new Date());

        Expense pizza = expenseRepository.addExpense("Pizza", 80, new Date(), food);
        Expense hm= expenseRepository.addExpense("H&M", 80, new Date(), clothes);


        expenseRepository.deleteExpense(hm);
        expenseRepository.deleteExpense(pizza);
        int totalSpentMoneyAfterUpdating = expenseRepository.getTotalMoneySpend(new Date());

        assertEquals(0,totalSpentMoneyAfterUpdating);
    }


    @Test
    public void getInstanceTest(){
        ExpenseRepository expenseRepository1 = ExpenseRepository.getInstance(appContext);

        boolean sameHashCode = expenseRepository1.hashCode() == expenseRepository.hashCode();

        assertTrue(sameHashCode);
    }


}
