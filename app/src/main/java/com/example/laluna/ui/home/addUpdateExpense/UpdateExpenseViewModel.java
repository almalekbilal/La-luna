package com.example.laluna.ui.home.addUpdateExpense;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laluna.Model.categoryAndExpense.Category;
import com.example.laluna.Model.repository.CategoryRepository;
import com.example.laluna.Model.categoryAndExpense.Expense;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.Date;
import java.util.List;



/**
 * UpdateExpenseViewModel is a class which is responsible for the communication with the data base handler
 *  and the logic (related to UpdateExpense Activity ) behind the scene.
 *
 *
 *   @auther (Bilal Almalek)
 *   @auther (Ali Malla)
 */

public class UpdateExpenseViewModel extends ViewModel {
    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;
    private Context context;
    private List<Category> categories;
    private Category selectedCategory;
    private Expense thisExpense;

    private MutableLiveData<String[]> categoriesNames;
    public UpdateExpenseViewModel(){

    }


/**
 * The method starts running once the class is running for the first time.
 * The DBHandler data field is initialized here.
 * It updates the data och sends it to the view
 */

    public void init(Context context, Expense thisExpense){
        this.context = context;
        expenseRepository = ExpenseRepository.getInstance(context);
        categoryRepository = CategoryRepository.getInstance(context);
        this.thisExpense = thisExpense;
        categoriesNames = new MutableLiveData<>();
        categoriesNames.postValue(getCategoriesNames());

    }

    private String[] getCategoriesNames(){

        categories = categoryRepository.getCategories(new Date());
        makeExpenseCategoryAsFirstCategory();

        selectedCategory = categories.get(0);
        String [] names = new String[categories.size()];

        for(int i = 0; i < names.length; i++){
            names[i] = categories.get(i).get_name();
        }

        return names;
    }


    private void makeExpenseCategoryAsFirstCategory(){
        if(categories.contains(thisExpense.get_category())){
            categories.remove(thisExpense.get_category());
        }
        categories.add(0,thisExpense.get_category());
    }


    public void setSelectedCategory(int position){
        selectedCategory = categories.get(position);
    }


    /**
     * Updates expenses in the database
     *
     * */
    public void updateExp(String name, int value){
        thisExpense.set_name(name);
        thisExpense.set_value(value);
        thisExpense.set_category(selectedCategory);

        expenseRepository.updateExpense(thisExpense);
    }

    public void deleteExpense(){
        expenseRepository.deleteExpense(thisExpense);
    }
    /**
     * A getter method
     * @return LiveData that contains an array of strings (categories names)
     */
    public LiveData<String[]> getcategoriesNames(){return categoriesNames;}


}
