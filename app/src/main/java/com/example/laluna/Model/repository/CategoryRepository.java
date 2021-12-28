package com.example.laluna.Model.repository;

import android.content.Context;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.CategoryWithExpenses;
import com.example.laluna.Model.DateConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryRepository {

    private DBHandler db;
    private static CategoryRepository repo = null;

    private CategoryRepository(Context context) {
        db = new DBHandler(context);
    }

    public List<Category> getCategories(Date date) {
        List<Category> categories = db.getCategories(date);

        return categories;
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

        if(DateConverter.isThisMonth(date)){
            categoryLimit = category.get_limit();
        }else{
            categoryLimit = db.getCategoryLimit(date, category);
        }
        return categoryLimit;
    }

	// Fix this
    public boolean thereIsCategories(Date date){
        return db.getCategories(date).size() != 0;
    }

    public boolean thereIsCategories(){
        return db.thereIsCategories();
    }

    public void addCategory(String name, int limit, int pictureName,String color, Date creation) {
       db.addCategory(name, limit, pictureName, color, creation);
    }

    public void deactivateCategory(Category category, Date date) {
        db.deactivateCategory(category, date);
    }

    public static CategoryRepository getInstance(Context context){
        if(repo == null){
            repo = new CategoryRepository(context);
        }

        return repo;
    }

}
