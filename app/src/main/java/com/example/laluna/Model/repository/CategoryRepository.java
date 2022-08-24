package com.example.laluna.Model.repository;

import android.content.Context;

import com.example.laluna.Model.calculations.Arithmetic;
import com.example.laluna.Model.categoryAndExpense.Category;
import com.example.laluna.Model.categoryAndExpense.CategoryWithExpenses;
import com.example.laluna.Model.DateConverter;
import com.example.laluna.Model.databaseService.IDatabaseHandler;
import com.example.laluna.Model.databaseService.SqliteHandler;
import com.example.laluna.Model.exceptions.CategoryIrrelevantLimitException;
import com.example.laluna.Model.exceptions.CategoryIrrelevantNameException;
import com.example.laluna.Model.exceptions.IrrelevantDateOfACategory;
import com.example.laluna.Model.exceptions.NoLimitExistingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryRepository {

    private IDatabaseHandler db;
    private static CategoryRepository repo = null;

    private CategoryRepository(Context context) {
        db = new SqliteHandler(context, null, null, 0);
    }

    public List<Category> getCategories(Date date) {
        List<Category> categories = db.getCategories(date);

        return categories;
    }

    public int getTotalBudget(Date date){
        return Arithmetic.calculateTotalBudget(getCategoriesWithExpenses(date));
    }

    public List<CategoryWithExpenses> getCategoriesWithExpenses(Date date){

        List<Category> categories = getCategories(date);
        List<CategoryWithExpenses> categoriesWithExpenses = new ArrayList<>();
        for(Category cat : categories){
            Date end = DateConverter.incrementMonth(date);
            CategoryWithExpenses categoryWithExpenses = new CategoryWithExpenses(cat,db.getCategoryExpensesByDate(date,end,cat), getCategoryLimit(date,cat));
            categoriesWithExpenses.add(categoryWithExpenses);
        }

        return categoriesWithExpenses;

    }


    public int getCategoryLimit(Date date, Category category){ // Can use Exception
        int categoryLimit = -1;
        try {
        if(date.before(category.getCreationDate())){
                throw new IrrelevantDateOfACategory("The date cannot be before the creation date of the category");
        }
        } catch (IrrelevantDateOfACategory irrelevantDateOfACategory) {
            irrelevantDateOfACategory.printStackTrace();
        }


        if(DateConverter.isThisMonth(date)){
            categoryLimit = category.get_limit();
        }else{

            try {
                categoryLimit = db.getCategoryLimit(date, category);
            } catch (NoLimitExistingException e) {
                e.printStackTrace();
            }
        }
        return categoryLimit;
    }

    public boolean thereIsCategories(Date date){
        return db.getCategories(date).size() != 0;
    }

    public boolean thereIsCategories(){
        return db.thereIsCategories();
    }

    public Category addCategory(String name, int limit, int pictureName,String color, Date creation) {
        try {
            if(limit < 0) {
                throw new CategoryIrrelevantLimitException();
            }

            if(name.equals("") || name == null){
                throw new CategoryIrrelevantNameException();
            }
        } catch (CategoryIrrelevantLimitException e) {
            e.printStackTrace();
        }catch (CategoryIrrelevantNameException e) {
            e.printStackTrace();
        }
        return db.addCategory(name, limit, pictureName, color, creation);
    }

    public void updateCategory(Category cat){
        db.updateCategory(cat);
    }

    public void deactivateCategory(Category category, Date date) {
        db.deactivateCategory(category, date);
    }

    public void setCategoriesPreviousLimits(Date date){
        db.setCategoriesPreviousLimits(date);
    }
    public static CategoryRepository getInstance(Context context){
        if(repo == null){
            repo = new CategoryRepository(context);
        }

        return repo;
    }



}
