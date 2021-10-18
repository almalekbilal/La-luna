package com.example.laluna.ui.addudateexpense;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;
import com.example.laluna.Model.Expense;

import java.util.Date;
import java.util.List;

public class UpdateExpenseViewModel extends ViewModel {
    private DBHandler dbHandler;
    private Context context;
    private List<Category> categories;
    private Category selectedCategory;
    private Expense thisExpense;

    private MutableLiveData<String[]> categoriesNames;
    public UpdateExpenseViewModel(){

    }


    public void init(Context context, Expense thisExpense){
        this.context = context;
        this.thisExpense = thisExpense;
        dbHandler = new DBHandler(context);
        categoriesNames = new MutableLiveData<>();
        categoriesNames.postValue(getCategoriesNames());
    }

    private String[] getCategoriesNames(){

        categories = dbHandler.getCategories(new Date());
        if(categories.contains(thisExpense.get_category())){
            categories.remove(thisExpense.get_category());
        }
        categories.add(0,thisExpense.get_category());

        selectedCategory = categories.get(0);
        String [] names = new String[categories.size()];

        for(int i = 0; i < names.length; i++){
            names[i] = categories.get(i).get_name();
        }

        return names;
    }

    public LiveData<String[]> getcategoriesNames(){return categoriesNames;}


}
