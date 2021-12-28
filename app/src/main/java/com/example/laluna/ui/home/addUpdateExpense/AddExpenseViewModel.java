package com.example.laluna.ui.home.addUpdateExpense;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.repository.DBHandler;

import java.util.Date;
import java.util.List;

/**
 * A ViewModel class that makes it possible for models and view (AddExpense view) to communicate with each other indirectly
 *
 * @author Bilal Al Malek
 * @author Ali Malla
 */
public class AddExpenseViewModel extends ViewModel {
    private DBHandler dbHandler;
    private Context context;   /// whyyyyy
    private List<Category> categories;
    private Category selectedCategory;

    private MutableLiveData<String[]> categoriesNames;
    public AddExpenseViewModel(){

    }

    /**
     * This method is responsible for initializing all data fields
     * @param context
     */
    public void init(Context context){
        this.context = context;
        dbHandler = new DBHandler(context);
        categoriesNames = new MutableLiveData<>();
        categoriesNames.postValue(getCategoriesNames());
    }


    /**
     * A method for adding a new expense. It communicates with the database.
     * @param name The name of the new expense
     * @param value The value of the new expense
     */
    public void addExp(String name, int value){
        dbHandler.addExpense(name,value,new Date(),selectedCategory);
        Toast.makeText(context,"Expense is added", Toast.LENGTH_LONG).show();
    }

    /**
     * A setter method
     * @param position
     */
    public void setSelectedCategory(int position){
        selectedCategory = categories.get(position);
    }

    private String[] getCategoriesNames(){

        categories = dbHandler.getCategories(new Date());
        selectedCategory = categories.get(0);
        String [] names = new String[categories.size()];

        for(int i = 0; i < names.length; i++){
            names[i] = categories.get(i).get_name();
        }

        return names;
    }

    /**
     * A getter method
     * @return LiveData that contains an array of strings (categories names)
     */
    public LiveData<String[]> getcategoriesNames(){return categoriesNames;}

}
