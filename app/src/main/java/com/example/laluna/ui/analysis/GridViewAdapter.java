package com.example.laluna.ui.analysis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laluna.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
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




    private List<CategoryWithMoney> categoryWithMoneyList; // a list of categories that will be placed in gridView



    private double total;   // category budget
    private double spent;   // category spent money


    public Context context;
    public LayoutInflater layoutInflater;


    private TextView categoryAnalysisBudget;
    private PieChart categoryPieCh;

    public GridViewAdapter(List<CategoryWithMoney> categoryWithMoneyList,Context context) {
        super(context, R.layout.customcategoryanalysis_analysis,categoryWithMoneyList);
        this.categoryWithMoneyList = categoryWithMoneyList;
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
        spent = getSpent(i);
        total = getTotal(i);
        showCategoryNameAndSpentMoney(i);
        makeCategoryPie(categoryPieCh,i);

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

        categoryAnalysisBudget.setText(categoryWithMoneyList.get(position).limit + " Kr");
    }

    /**
     * A method to get a category spent money
     * @param i category position in the list
     * @return category spent money
     */
    private double getSpent(int i){
       return categoryWithMoneyList.get(i).spent;
    }

    /**
     * A method to get a category budget
     * @param i category position in the list
     * @return category budget
     */
    private double getTotal(int i){
        return categoryWithMoneyList.get(i).limit;
    }

    /**
     * A method to show the spent money and name to a specific category in the center of the pie chart
     * @param i category position in the list
     */
    private void showCategoryNameAndSpentMoney(int i){
        categoryPieCh.setCenterText(categoryWithMoneyList.get(i).category.get_name() + " \n" + spent + " Kr");
    }


    /**
     * The method makes the circle diagram for the category and puts the information in it
     * @param piechart
     */
    private void makeCategoryPie(PieChart piechart, int i) {
        piechart.setUsePercentValues(true);

        piechart.setHoleRadius(85f);
        piechart.setTransparentCircleRadius(85f);

        double precent = (spent/total) * 100;
        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry( (float)precent,"Spent"));
        value.add(new PieEntry((float)(100-precent),"Left"));

        PieDataSet dataSet = new PieDataSet(value,null);


        PieData pieData = new PieData(dataSet);

        List<Integer> colors = new ArrayList<>();
        colors.add(Integer.parseInt(categoryWithMoneyList.get(i).category.get_color()));
        //colors.add(Color.rgb(3, 169, 241));
        //colors.add(Color.parseColor(categoryWithMoneyList.get(i).category.get_color()));
        colors.add(Color.rgb(203, 204, 196));


        dataSet.setColors(colors);
        dataSet.setValueTextSize(0);
        piechart.setData(pieData);

        piechart.setCenterTextSize(15);
        piechart.setCenterTextColor(Color.rgb(255, 255, 255));
        piechart.setHoleColor(Color.rgb(40, 43, 51));
        piechart.setDescription(null);

        piechart.getLegend().setEnabled(false);

        piechart.setDrawEntryLabels(false);
        piechart.setEnabled(false);
        piechart.setEnabled(true);
    }

}
