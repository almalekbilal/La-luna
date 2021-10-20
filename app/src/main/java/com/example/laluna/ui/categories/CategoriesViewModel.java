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

/**
 *  ViewModel class that is responsible for the communication with the data base handler
 *  and the logic (related to Category fragment) behind the scene.
 *
 * @author Ali Alkhaled
 * @author Deaa Khankan
 */

public class CategoriesViewModel extends ViewModel {

    private List<Category> categoryList = new ArrayList<>();
    private MutableLiveData <List<Category>> categoryMutableLive= new MutableLiveData<>();
    private DBHandler db;


    /**
     * The method starts running once the class is running for the first time.
     * The DBHandler data field is initialized here.
     * It updates the data och sends it to the view
     * @param context The android component that has to be connected to the database
     */
    public void init(Context context) {
        db = new DBHandler(context);
        categoryMutableLive.postValue(db.getCategories(new Date()));
        updateCategories();
    }


    //Helper
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

    /**
     * A getter method
     * @return List of category
     */
    public List<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * A getter method
     * @return List of category (Live Data)
     */
    public LiveData <List<Category>> getCategory() {
        return categoryMutableLive;
    }

    /**
     * A method for adding a new category from the view. (By user)
     * It communicates with the data base
     * @param name Name of the new category
     * @param limit Limit of the new category
     * @param pictureName picture code of the new category
     * @param color color code of the new category
     * @param dateCreation the date when the new category will be created
     */
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
}

