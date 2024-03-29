package com.example.laluna;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.laluna.Model.categoryAndExpense.Category;
import com.example.laluna.Model.average.Average;
import com.example.laluna.Model.average.AverageFactory;
import com.example.laluna.Model.exceptions.IrrelevantDateException;
import com.example.laluna.Model.repository.CategoryRepository;
import com.example.laluna.Model.repository.ExpenseRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;


/////////////////////////////////////////////////////NOT COMPLETED YET///////////////////////////////////////
@RunWith(AndroidJUnit4.class)
public class AverageTest {

    private Average average;

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
    public void dayAverageTest(){

        Category c1 = categoryRepository.addCategory("food",100,0, "blue",new Date(121,5,1));

        expenseRepository.addExpense("Book", 21, new Date(122, 6, 1), c1);
        expenseRepository.addExpense("Notebook", 9, new Date(122, 6, 5), c1);

        Calendar start = Calendar.getInstance();
        Calendar end = (Calendar) start.clone();


        start.add(Calendar.MONTH, -1);
        try {
            average = AverageFactory.getMonthAvarage(start, end, expenseRepository);
        } catch (IrrelevantDateException e) {
            e.printStackTrace();
        }

        double actual = average.getAvarage();
        double expected = 15;

        Log.e("testAvarage",average.getTimesList().size() + "");

        assertEquals(expected, actual, 0.001);


    }

    @Test
    public void monthAverageTest(){

        Category c1 = categoryRepository.addCategory("food",100,0, "blue",new Date(121,5,1));

        expenseRepository.addExpense("Book", 21, new Date(121, 11, 1), c1);
        expenseRepository.addExpense("Notebook", 9, new Date(121, 11, 5), c1);
        expenseRepository.addExpense("Book", 40, new Date(121, 10, 1), c1);
        expenseRepository.addExpense("Notebook", 10, new Date(121, 10, 5), c1);

        Calendar start = Calendar.getInstance();
        Calendar end = (Calendar) start.clone();


        end.add(Calendar.MONTH, -3);
        try {
            average = AverageFactory.getMonthAvarage(end, start, expenseRepository);
        } catch (IrrelevantDateException e) {
            e.printStackTrace();
        }


        assertEquals(30, (int)average.getTimesList().get(0).getValue());


    }
}
