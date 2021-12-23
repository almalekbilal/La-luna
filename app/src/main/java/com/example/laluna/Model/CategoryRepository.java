package com.example.laluna.Model;

import android.content.Context;

import java.util.Date;
import java.util.List;

public class CategoryRepository {

    private DBHandler db;

    public CategoryRepository(Context context) {
        db = new DBHandler(context);
    }

    public List<Category> getCategories(Date date) {
        List<Category> categories = db.getCategories(date);

        return categories;
    }


    public int getCategoryLimit(Date date, Category category){ // Can use Exception
        int categoryLimit = -1;
        for(Category cat : db.getCategories(date)){
            if(cat.get_id() == category.get_id()){
                categoryLimit = cat.get_limit();
            }
        }
        return categoryLimit;
    }

    public boolean thereIsCategories(Date date){
        return db.getCategories(date).size() != 0;
    }

    public void addCategory(String name, int limit, int pictureName,String color, Date creation) {
       db.addCategory(name, limit, pictureName, color, creation);
    }

    public void deactivateCategory(Category category, Date date) {
        db.deactivateCategory(category, date);
    }

}
