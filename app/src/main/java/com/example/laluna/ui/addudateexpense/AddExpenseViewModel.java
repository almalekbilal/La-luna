package com.example.laluna.ui.addudateexpense;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;

import java.util.Date;
import java.util.List;

public class AddExpenseViewModel extends ViewModel {
    private DBHandler dbHandler;
    private Context context;
    private List<Category> categories;
    private Category selectedCategory;

    private MutableLiveData<String[]> categoriesNames;
    public AddExpenseViewModel(){

    }

    public void init(Context context){
        this.context = context;
        dbHandler = new DBHandler(context);
        categoriesNames = new MutableLiveData<>();
        categoriesNames.postValue(getCategoriesNames());
    }

    public void addExp(String name, int value){
        dbHandler.addExpense(name,value,new Date(),selectedCategory);
        Toast.makeText(context,"Expense is added", Toast.LENGTH_LONG).show();
    }

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

    public LiveData<String[]> getcategoriesNames(){return categoriesNames;}

}
