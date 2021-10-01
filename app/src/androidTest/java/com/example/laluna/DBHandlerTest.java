package com.example.laluna;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.laluna.DatabaseClasses.Category;
import com.example.laluna.DatabaseClasses.DBHandler;
import com.example.laluna.DatabaseClasses.Expense;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class DBHandlerTest {


    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    DBHandler db = new DBHandler(appContext,null,null,1);

    //List<Category> categories = db.getCategories(new Date());

    @Before
    public void deleteDB(){
        appContext.deleteDatabase(db.getDatabaseName());
    }

    @Test
    public void addCategoryTest(){


        Category Apartment = db.addCategory("Apartment", 500,null,null,new Date());


        List<Category> categories = db.getCategories(new Date());

        boolean categoryAdded = false;
        for(int i=0; i<categories.size(); i++){
            if(categories.get(i).get_name().equals(Apartment.get_name()) && categories.get(i).get_limit() == Apartment.get_limit()
            && categories.get(i).get_id() == Apartment.get_id()){
                categoryAdded = true;
            }
        }

       assertEquals(true,categoryAdded);



    }

    @Test
    public void addExpenseTest(){

        List<Category> categories = db.getCategories(new Date());

        int ApartmentIndexOnCategories = 0;
        boolean ApartmentAvailable = false;


        for(int i=0; i<categories.size(); i++){
            if(categories.get(i).get_name().equals("Apartment")){
                ApartmentAvailable = true;
                ApartmentIndexOnCategories = i;
            }
        }

        List<Expense> apartmentExpenses;
        Expense expense;

        boolean a = false;
        if(ApartmentAvailable){
            expense = db.addExpense("Furniture",200,new Date(),categories.get(ApartmentIndexOnCategories));
            apartmentExpenses = db.getCategoryExpense(categories.get(ApartmentIndexOnCategories));

        }
        else{
            Category newCategory = db.addCategory("Apartment",500,null,null,new Date());
            expense = db.addExpense("Furniture",200,new Date(),newCategory);
            apartmentExpenses = db.getCategoryExpense(newCategory);
        }


        boolean expenseAdded = false;

        for(int i=0; i<apartmentExpenses.size(); i++){
            if(apartmentExpenses.get(i).get_name().equals(expense.get_name())
            && apartmentExpenses.get(i).get_value() == expense.get_value() && apartmentExpenses.get(i).get_id() == expense.get_id()
                    && apartmentExpenses.get(i).get_category().get_id() == expense.get_category().get_id()){

                expenseAdded = true;
            }
        }

        assertEquals(true,expenseAdded);

    }


    @Test
    public void getCategoryLimitTest(){
        Category Book = db.addCategory("Book",275,null,null,new Date());

        List<Category> categories = db.getCategories(new Date());

        int limit = 0;
        for (Category book: categories){
            if(book.get_name().equals(Book.get_name()) && book.get_limit() == Book.get_limit() && book.get_id() == Book.get_id()){
                limit = book.get_limit();
            }
        }
        assertEquals(275,limit);

    }


    @Test
    public void getExpensesTest(){
        Expense expense = db.addExpense("Hamburger",50,new Date(),
                db.addCategory("Food",500,null,null,new Date()));

        List<Expense> expanses = db.getExpenses(0,16);

        assertEquals(1,expanses.size());
    }




    // Failed
    @Test
    public void getTotalMoneySpentTest(){



        List<Expense> expanses = db.getExpenses(0,16);
        int spentMoney = 0;
        for(Expense expense : expanses){
            spentMoney += expense.get_value();
        }

        /*List<Category> fs = db.getCategories(new Date());
        db.addExpense("df",500,new Date(2021,6,1),fs.get(0));
        db.addExpense("df",400,new Date(2021,6,1),fs.get(0));
        db.addExpense("df",300,new Date(2021,6,1),fs.get(0));
        db.addExpense("df",150,new Date(2021,6,1),fs.get(0));*/


        int getSpentMoney = db.getTotalMoneySpent(new Date(2021,10,1));

        assertEquals(1350,getSpentMoney);
    }


    @Test
    public void deleteExpenseTest(){
        Category Book = db.addCategory("Book",275,null,null,new Date());

        Expense calculus = db.addExpense("Calculus",25,new Date(),Book);

        List<Expense> allExpansesBefore = db.getExpenses(0,16);
        db.deleteExpense(calculus);
        List<Expense> allExpansesAfter = db.getExpenses(0,16);



        /*List<Expense> allExpanses = db.getExpenses(0,16); // We need another method to get all Expenses
        boolean calculusDeleted = true;
        for (Expense expense: allExpanses){
            if( expense.get_name().equals(calculus.get_name()) && expense.get_id() == calculus.get_id()){
                calculusDeleted = false;

            }
        }*/


        //assertEquals(true,calculusDeleted);
        assertEquals(true,allExpansesBefore.size() == allExpansesAfter.size() + 1);

    }



}
