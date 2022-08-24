package com.example.laluna.Model.circleDiagrams;

import android.graphics.Color;

import com.example.laluna.Model.categoryAndExpense.CategoryWithExpenses;
import com.example.laluna.Model.categoryAndExpense.Expense;

import java.util.ArrayList;
import java.util.List;

public class CategoryCircleDiagram extends CircleDiagram{

    private CategoryWithExpenses categoryWithExpenses;

    public CategoryCircleDiagram(CategoryWithExpenses categoryWithExpenses) {

        super(categoryWithExpenses.getSpentMoney(), categoryWithExpenses.getCategoryLimit(), Color.parseColor(categoryWithExpenses.getCategory().get_color()));
        this.categoryWithExpenses = categoryWithExpenses;

    }

    private String getCategoryName(){
        return categoryWithExpenses.getCategory().get_name();
    }

    public List<Expense> getExpensesList(){
        return categoryWithExpenses.getCategoryExpenses();
    }

    public static List<CategoryCircleDiagram> getCircleDiagrams(List<CategoryWithExpenses> catExpenses){
        List<CategoryCircleDiagram> diagrams = new ArrayList<>();

        for(CategoryWithExpenses categoryExp : catExpenses){
            diagrams.add(new CategoryCircleDiagram(categoryExp));
        }

        return diagrams;
    }

    @Override
    public String getCenterText(){
        return getCategoryName() + " \n" + getSpent() + " Kr";
    }
}
