package com.example.laluna.ui.categories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CategoriesViewModel extends ViewModel {

    private List<Category> categoryList = new ArrayList<>();
    private MutableLiveData <List<Category>> categoryMutableLive= new MutableLiveData<>();
    private DBHandler db;


    public void init(Context context) {
        db = new DBHandler(context);
        categoryMutableLive.postValue(db.getCategories(new Date()));
        updateCategories();
    }


    private void updateCategories(){
        List<Category> cat = new ArrayList<>();

        List<Category> categories = db.getCategories(new Date());

        for(Category category : categories){
            cat.add(new Category(category.get_id(),category.get_limit(),category.get_name(),
                    category.get_pictureName(),category.get_color(),category.getCreationDate(),
                    category.getDestroyedDate()));

        }

        categoryMutableLive.postValue(cat);

    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public LiveData <List<Category>> getCategory() {
        return categoryMutableLive;
    }

    public void addCategory(String name, int limit, int pictureName, String color, Date dateCreation) {
        db.addCategory(name, limit, pictureName, color, dateCreation);
    }

    public void editCategory(String name,int id, int budget,String date, int picture, String color){


        Category category = new Category(id,budget,name,picture,color,stringToDate(date),null);
        db.updateCategory(category);
        updateCategories();
    }
   private Date stringToDate(String dateString){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if(dateString != null) {
                date = sdf.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


     /*public void editCategory(String name,int limit,int categoryId){
        List <Category> categories = db.getCategories(new Date());
         for(int i=0; i<db.getCategories(new Date()).size(); i++){
             if(categories.get(i).get_id() == categoryId){
                categories.get(i).set_name(name);
                 categories.get(i).set_limit(limit);
             }
         }
         categoryMutableLive.postValue(categories);
     }
     /
      */

}

