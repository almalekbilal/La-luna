package com.example.laluna;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.Category;
import com.example.laluna.Model.CategoryWithExpenses;
import com.example.laluna.Model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ArithmeticTest {


    @Test
    public void calculateTotalMoneySpentTest(){
        List<Expense> expenses = new ArrayList<>();

        expenses.add(new Expense(1,"burger",50,new Date(),null));
        expenses.add(new Expense(2,"burger",50,new Date(),null));
        expenses.add(new Expense(3,"burger",50,new Date(),null));
        expenses.add(new Expense(4,"burger",50,new Date(),null));
        expenses.add(new Expense(5,"burger",50,new Date(),null));

        int totalMoneySpent = Arithmetic.calculateTotalMoneySpent(expenses);

        assertEquals(250,totalMoneySpent);
    }

    @Test
    public void calculateTotalBudgetTest(){
        List<CategoryWithExpenses> categoryWithExpensesArrayList = new ArrayList<>();

        Category food = new Category(1,500,"Food",2,"black",new Date(),null);
        Category food1 = new Category(2,400,"Food1",2,"black",new Date(),null);
        Category food2 = new Category(3,300,"Food2",2,"black",new Date(),null);

        categoryWithExpensesArrayList.add(new CategoryWithExpenses(food,new ArrayList<Expense>(),500));
        categoryWithExpensesArrayList.add(new CategoryWithExpenses(food1,new ArrayList<Expense>(),400));
        categoryWithExpensesArrayList.add(new CategoryWithExpenses(food2,new ArrayList<Expense>(),300));



        int totalBudget = Arithmetic.calculateTotalBudget(categoryWithExpensesArrayList);

        assertEquals(1200,totalBudget);
    }
}