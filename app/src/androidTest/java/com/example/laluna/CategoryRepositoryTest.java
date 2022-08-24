package com.example.laluna;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.laluna.Model.categoryAndExpense.Category;
import com.example.laluna.Model.categoryAndExpense.CategoryWithExpenses;
import com.example.laluna.Model.repository.CategoryRepository;
import com.example.laluna.Model.repository.ExpenseRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CategoryRepositoryTest {

    private Context appContext;

    private CategoryRepository categoryRepository;


    @Before
    public void destroyDataBase() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
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
    public void addCategoryTest() {

        Category c1 = categoryRepository.addCategory("food", 100, 0, null, new Date(121, 01, 01));
        categoryRepository.addCategory("car", 500, 0, "red", new Date(120, 01, 01));

        List<Category> categories = categoryRepository.getCategories(new Date(121, 01, 03));
        assertEquals(categories.size(), 2);

    }

    @Test
    public void getCategoriesTest() {

        categoryRepository.addCategory("food", 100, 0, "blue", new Date(120, 05, 01));

        categoryRepository.addCategory("car", 500, 0, "red", new Date(120, 05, 01));

        categoryRepository.addCategory("clothes", 100, 0, "blue", new Date(120, 04, 01));

        categoryRepository.addCategory("other", 500, 0, "red", new Date(120, 05, 01));

        List<Category> categories = categoryRepository.getCategories(new Date());
        assertEquals(4,categories.size());
    }

    @Test
    public void getTotalBudgetTest(){
        categoryRepository.addCategory("food", 100, 0, "blue", new Date());

        categoryRepository.addCategory("car", 500, 0, "red", new Date());

        categoryRepository.addCategory("clothes", 100, 0, "blue", new Date());



        int totalBudget = categoryRepository.getTotalBudget(new Date());

        assertEquals(700,totalBudget);
    }


    @Test
    public void getCategoriesWithExpensesTest(){
        categoryRepository.addCategory("food", 100, 0, "blue", new Date(120, 05, 01));

        categoryRepository.addCategory("car", 500, 0, "red", new Date(120, 05, 01));

        categoryRepository.addCategory("clothes", 100, 0, "blue", new Date(120, 03, 01));

        categoryRepository.addCategory("other", 500, 0, "red", new Date(120, 05, 01));

        //categoryRepository.setCategoriesPreviousLimits(new Date(120, 05, 01)); //why we need this method here????
        List<CategoryWithExpenses> categoryWithExpenses = categoryRepository.getCategoriesWithExpenses(new Date(120, 05, 01)); // Problem in Date
        //List<CategoryWithExpenses> categoryWithExpenses = categoryRepository.getCategoriesWithExpenses(new Date());
        assertEquals(4,categoryWithExpenses.size());
    }



    @Test
    public void getCategoryLimitTest(){
        Category food = categoryRepository.addCategory("food", 100, 0, "blue", new Date());

        Category car = categoryRepository.addCategory("car", 500, 0, "red", new Date());

        Category clothes = categoryRepository.addCategory("clothes", 100, 0, "blue", new Date());

        Category other = categoryRepository.addCategory("other", 500, 0, "red", new Date());

        int carLimit = categoryRepository.getCategoryLimit(new Date(),car);

        assertEquals(500,carLimit);
    }


    @Test
    public void thereIsCategoriesByDate(){
        categoryRepository.addCategory("food", 100, 0, "blue", new Date());

        categoryRepository.addCategory("car", 500, 0, "red", new Date());

        categoryRepository.addCategory("clothes", 100, 0, "blue", new Date());

        categoryRepository.addCategory("other", 500, 0, "red", new Date());

        boolean thereIsCategories = categoryRepository.thereIsCategories(new Date());

        assertTrue(thereIsCategories);
    }

    @Test
    public void thereIsCategoriesTest(){

        boolean thereIsCategories = categoryRepository.thereIsCategories();

        boolean thereIsNoCategories = !(thereIsCategories);

        assertTrue(thereIsNoCategories);
    }



    @Test
    public void updateCategoryTest(){
        Category food = categoryRepository.addCategory("food", 100, 0, "blue", new Date());
        Category clothes = categoryRepository.addCategory("clothes", 100, 0, "blue", new Date());

        food.set_limit(500);
        clothes.set_limit(250);
        categoryRepository.updateCategory(food);
        categoryRepository.updateCategory(clothes);
        int totalBudgetAfterUpdating = categoryRepository.getTotalBudget(new Date());

        assertEquals(750,totalBudgetAfterUpdating);
    }


    @Test
    public void deactivateCategoryTest() {

        Category category1 = categoryRepository.addCategory("food", 100, 0, "blue", new Date(121, 01, 01));

        Category category2 = categoryRepository.addCategory("car", 500, 0, "green", new Date(120, 01, 01));

        categoryRepository.deactivateCategory(category1, new Date(121, 02, 01));

        categoryRepository.deactivateCategory(category2, new Date(121, 02, 01));

        List<Category> categories2 = categoryRepository.getCategories(new Date(120, 9, 01));

        assertEquals(1, categories2.size());

    }

    @Test
    public void deactivateCategoryInTheSameMonthTest() {

        Category category1 = categoryRepository.addCategory("cat1", 100, 0, "blue", new Date(121, 03, 27));

        Category category2 = categoryRepository.addCategory("cat2", 400, 0, "green", new Date(121, 04, 15));

        Category category3 = categoryRepository.addCategory("cat3", 1050, 0, "blue", new Date(121, 05, 9));

        Category category4 = categoryRepository.addCategory("cat4", 850, 0, "green", new Date(121, 06, 22));

        List <Category> categories = categoryRepository.getCategories(new Date(121, 7, 1));

        boolean hasBeenAdded = categories.contains(category1) && categories.contains(category2) &&
                categories.contains(category3) && categories.contains(category4);

        categoryRepository.deactivateCategory(category1, new Date(121, 07, 1));

        categoryRepository.deactivateCategory(category2, new Date(121, 07, 2));

        categoryRepository.deactivateCategory(category3, new Date(121, 07, 3));

        categoryRepository.deactivateCategory(category4, new Date(121, 07, 4));

        categories = categoryRepository.getCategories(new Date(121, 7, 5));

        boolean hasBeenDeactivated = hasBeenAdded && categories.size()==0;

        assertTrue(hasBeenDeactivated);


    }


    @Test
    public void deactivateCategoryInTheSameDayTest() {

        Category category1 = categoryRepository.addCategory("cat1", 100, 0, "blue", new Date(121, 03, 27));

        Category category2 = categoryRepository.addCategory("cat2", 400, 0, "green", new Date(121, 04, 15));

        Category category3 = categoryRepository.addCategory("cat3", 1050, 0, "blue", new Date(121, 05, 9));

        categoryRepository.deactivateCategory(category1, new Date(121, 06, 4));

        categoryRepository.deactivateCategory(category2, new Date(121, 06, 5));

        categoryRepository.deactivateCategory(category3, new Date(121, 06, 4));

        List<Category> testCategoryList = categoryRepository.getCategories(new Date(121, 06, 4));

        assertEquals(3, testCategoryList.size());


    }


    @Test
    public void deactivateCategoryMoreConditionsTest() {

        Category category1 = categoryRepository.addCategory("food", 100, 0, "blue", new Date(121, 01, 01)); // does not count

        Category category2 = categoryRepository.addCategory("car", 500, 0, "green", new Date(120, 01, 01));

        Category category3 = categoryRepository.addCategory("truck", 1000, 0, "red", new Date(120, 05, 01));

        Category category4 = categoryRepository.addCategory("pizza", 52, 0, "black", new Date(120, 10, 01)); // does not count

        Category category5 = categoryRepository.addCategory("Other", 1000, 0, "white", new Date(120, 05, 01));

        Category category6 = categoryRepository.addCategory("Drinks", 400, 0, "red", new Date(120, 12, 01)); // does not count

        Category category7 = categoryRepository.addCategory("Uni", 350, 0, "green", new Date(120, 9, 02)); // does not count

        Category category8 = categoryRepository.addCategory("Clothes", 900, 0, "blue", new Date(120, 8, 01));

        Category category9 = categoryRepository.addCategory("House", 1200, 0, "red", new Date(120, 07, 01));

        Category category10 = categoryRepository.addCategory("Night out", 1000, 0, "black", new Date(120, 01, 01));


        categoryRepository.deactivateCategory(category1, new Date(121, 02, 01)); // will be counted

        categoryRepository.deactivateCategory(category2, new Date(121, 02, 01)); // will be counted

        categoryRepository.deactivateCategory(category5, new Date(120, 10, 30)); // will be counted

        categoryRepository.deactivateCategory(category3, new Date(120, 9, 30)); // will be counted

        categoryRepository.deactivateCategory(category9, new Date(120, 8, 30)); //will not be counted

        categoryRepository.deactivateCategory(category10, new Date(120, 8, 01)); //will not be counted

        List<Category> categories2 = categoryRepository.getCategories(new Date(120, 9, 01));

        assertEquals(categories2.size(), 5);

    }


    @Test
    public void setCategoriesPreviousLimitsTest(){

        categoryRepository.addCategory("food", 100, 0, "blue", new Date(120,05,01));

        categoryRepository.addCategory("car", 500, 0, "red", new Date(120,05,01));

        categoryRepository.addCategory("clothes", 100, 0, "blue", new Date());


        //categoryRepository.setCategoriesPreviousLimits(new Date(120,05,01)); // we can catch exception if we have not this method here
        categoryRepository.setCategoriesPreviousLimits(new Date(120,05,01));

        int totalBudget = categoryRepository.getTotalBudget(new Date(120,05,01));

        assertEquals(600,totalBudget);

    }


    @Test
    public void getInstanceTest(){
        CategoryRepository categoryRepository1 = CategoryRepository.getInstance(appContext);

        boolean sameHashCode = categoryRepository1.hashCode() == categoryRepository.hashCode();

        assertTrue(sameHashCode);
    }


    @Test
    public void getCategorySpentMoneyTest(){

        ExpenseRepository expenseRepository = ExpenseRepository.getInstance(appContext);

        Category food = categoryRepository.addCategory("food", 100, 0, "blue", new Date());

        expenseRepository.addExpense("burger",50,new Date(),food);


        List<CategoryWithExpenses> categoryWithExpenses = categoryRepository.getCategoriesWithExpenses(new Date());
        int categorySpentMoney = categoryWithExpenses.get(0).getSpentMoney();
        assertEquals(categorySpentMoney, 50);
    }

}
