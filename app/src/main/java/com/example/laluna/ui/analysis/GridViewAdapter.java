package com.example.laluna.ui.analysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laluna.Model.categoryAndExpense.CategoryWithExpenses;
import com.example.laluna.Model.circleDiagrams.CategoryCircleDiagram;
import com.example.laluna.R;
import com.example.laluna.ui.PieChartMaker;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

/**
 *
 * The adapter that controls the gridview in the view
 * It take the data and place them in the gridview as rows and columns
 *
 *   @auther (Bilal Al Malek)
 *   @auther (Ali Malla)
 */
public class GridViewAdapter extends ArrayAdapter {




    private List<CategoryWithExpenses> categoryWithExpensesList; // a list of categories that will be placed in gridView



    public Context context;
    public LayoutInflater layoutInflater;


    private TextView categoryAnalysisBudget;
    private PieChart categoryPieCh;

    public GridViewAdapter(List<CategoryWithExpenses> categoryWithExpensesList,Context context) {
        super(context, R.layout.customcategoryanalysis_analysis,categoryWithExpensesList);
        this.categoryWithExpensesList = categoryWithExpensesList;
        this.context = context;

    }

    /**
     * The method create the view for one item and place the information in it
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(layoutInflater == null) {
            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null){
            view = layoutInflater.inflate(R.layout.customcategoryanalysis_analysis,null);
        }

        init(view);
        showCategoryBudget(i);
        PieChartMaker.makePie(categoryPieCh,new CategoryCircleDiagram(categoryWithExpensesList.get(i)));

        return view;
    }


    /**
     * This method initializes the class's attributes that are not initialized in constructor or in getView method
     * @param view a category view in analysis fragment
     */
    private void init(View view){
        categoryAnalysisBudget = view.findViewById(R.id.categoryAnalysisBudget);
        categoryPieCh = view.findViewById(R.id.categoryPieCh);
    }

    /**
     * A method to show the category budget in grid view
     * @param position category poistion in the list
     */
    private void showCategoryBudget(int position){

        categoryAnalysisBudget.setText(categoryWithExpensesList.get(position).getCategoryLimit() + " Kr");
    }


}
