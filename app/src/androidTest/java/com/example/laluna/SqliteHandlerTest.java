package com.example.laluna;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;


import com.example.laluna.Model.Category;
import com.example.laluna.Model.Expense;
import com.example.laluna.Model.SqliteHandler;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class SqliteHandlerTest {


    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    SqliteHandler db = new SqliteHandler(appContext,null,null,1);

    //List<Category> categories = db.getCategoriesDB(new Date());

    @Before
    public void deleteDB(){
        appContext.deleteDatabase(db.getDatabaseName());
    }

    @Test
    public void addCategoryTest(){


        Category Apartment = db.addCategoryDB("Apartment", 500,R.drawable.food,null,new Date());


        List<Category> categories = db.getCategoriesDB(new Date());

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

        List<Category> categories = db.getCategoriesDB(new Date());

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
            expense = db.addExpenseDB("Furniture",200,new Date(),categories.get(ApartmentIndexOnCategories));
            apartmentExpenses = db.getCategoryExpenseDB(categories.get(ApartmentIndexOnCategories).get_id());

        }
        else{
            Category newCategory = db.addCategoryDB("Apartment",500,R.drawable.food,null,new Date());
            expense = db.addExpenseDB("Furniture",200,new Date(),newCategory);
            apartmentExpenses = db.getCategoryExpenseDB(newCategory.get_id());
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
        Category Book = db.addCategoryDB("Book",275,R.drawable.food,null,new Date());

        List<Category> categories = db.getCategoriesDB(new Date());

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
        Expense expense = db.addExpenseDB("Hamburger",50,new Date(),
                db.addCategoryDB("Food",500,R.drawable.food,null,new Date()));

        List<Expense> expanses = db.getExpensesDB(0,16);

        assertEquals(1,expanses.size());
    }





    @Test
    public void getTotalMoneySpentTest(){




        db.addCategoryDB("da",1400,R.drawable.food,null,new Date());
        List<Category> fs = db.getCategoriesDB(new Date());
        db.addExpenseDB("df",500,new Date(121,10,1),fs.get(0));
        db.addExpenseDB("dfc",400,new Date(121,10,1),fs.get(0));
        db.addExpenseDB("dfg",300,new Date(121,10,1),fs.get(0));
        db.addExpenseDB("dfq",150,new Date(121,10,1),fs.get(0));

        List<Expense> expanses = db.getExpensesDB(0,4);
        int spentMoney = 0;
        for(Expense expense : expanses){
            spentMoney += expense.get_value();
        }

        int getSpentMoney = db.getTotalMoneySpentDB(new Date(2021,10,1));

        assertEquals(spentMoney,getSpentMoney);
    }


    @Test
    public void deleteExpenseTest(){
        Category Book = db.addCategoryDB("Book",275,R.drawable.food,null,new Date());

        Expense calculus = db.addExpenseDB("Calculus",25,new Date(),Book);

        List<Expense> allExpansesBefore = db.getExpensesDB(0,16);
        db.deleteExpenseDB(calculus);
        List<Expense> allExpansesAfter = db.getExpensesDB(0,16);



        /*List<Expense> allExpanses = db.getExpensesDB(0,16); // We need another method to get all Expenses
        boolean calculusDeleted = true;
        for (Expense expense: allExpanses){
            if( expense.get_name().equals(calculus.get_name()) && expense.get_id() == calculus.get_id()){
                calculusDeleted = false;

            }
        }*/


        //assertEquals(true,calculusDeleted);
        assertEquals(true,allExpansesBefore.size() == allExpansesAfter.size() + 1);

    }



    @Test
    public void updateCategoryTest(){
        Category food = db.addCategoryDB("Food",200,R.drawable.food,null,new Date());

        food.set_limit(300);

        db.updateCategoryDB(food);

        assertEquals(db.getCategoriesDB(new Date()).get(0).get_limit(),300);
    }


    @Test
    public void getCategoryExpense(){
        Category book = db.addCategoryDB("Book",500,R.drawable.food,null,new Date());

        Expense calculus = db.addExpenseDB("Calculus",50,new Date(),book);
        Expense designingInterface = db.addExpenseDB("DesigningInterface",60,new Date(),book);
        Expense discreteMathematics = db.addExpenseDB("DiscreteMathematics",70,new Date(),book);
        Expense linearAlgebra = db.addExpenseDB("LinearAlgebra",30,new Date(),book);

        List <Expense> allExpansesInBook = db.getCategoryExpenseDB(book.get_id());
        boolean allIsTrue = allExpansesInBook.get(0).get_id() == calculus.get_id() && allExpansesInBook.get(1).get_id() == designingInterface.get_id()
                && allExpansesInBook.get(2).get_id() == discreteMathematics.get_id()
                && allExpansesInBook.get(3).get_id() == linearAlgebra.get_id();


        assertEquals(true,allIsTrue);
    }


    @Test
    public void getTotalBudgetTest(){
        Category food = db.addCategoryDB("Food",200,R.drawable.food,null,new Date(121,5,1));
        db.addCategoryDB("Car",100,R.drawable.food,null,new Date(121,5,1));


        db.setCategoriesPreviousLimitsDB(new Date(121,5,1));

        int totalBudget= db.getTotalBudgetDB(new Date(121,5,1));


        assertEquals(totalBudget,300);
    }





}
