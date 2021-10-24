package com.example.laluna.ui.analysis;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;
import com.example.laluna.ui.analysis.categoryExpensesActivity.CategoryExpensesActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * View class that is responsible for showing the information to the user
 * It communicates the AnalysisViewModel
 *
 *   @auther (Bilal Al Malek)
 *   @auther (Ali Malla)
 */
public class AnalysisFragment extends Fragment {

    private AnalysisViewModel analysisViewModel;
    private GridViewAdapter gridViewAdapter;

    private double total , spent;


    private GridView gridViewAnalysis;
    private TextView dateText;
    private TextView budgetText;
    private PieChart piechart;
    private TextView leftArrow;
    private TextView rightArrow;

    private View root;
    private Intent intent;

    /**
     * This method creates the view and handle the widgets in it
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        init(inflater,container);
        makePie(piechart);
        openCategoryExpensesActivity();
        updateCategories();
        updateBigPieChart();
        showTotalBudget();
        updateDate();
        updateViewWhenClickOnLeftArrow();
        updateViewWhenClickOnRightArrow();

        return root;
    }



    /**
     * This method initializes the class's attributes
     * @param inflater
     * @param container
     */
    private void init(LayoutInflater inflater, ViewGroup container){
        analysisViewModel =  ViewModelProviders.of(this).get(AnalysisViewModel.class);
        root = inflater.inflate(R.layout.fragment_analysis, container, false);

        gridViewAnalysis = root.findViewById(R.id.gridViewAnalysis);
        dateText = root.findViewById(R.id.txv_date);
        budgetText = root.findViewById(R.id.txv_budgetAnalysis);
        leftArrow = root.findViewById(R.id.txv_leftArrow);
        rightArrow = root.findViewById(R.id.txv_rightArrow);
        piechart = root.findViewById(R.id.pc_categorySpent);

        gridViewAdapter = new GridViewAdapter(new ArrayList<CategoryWithMoney>(),root.getContext());

        gridViewAnalysis.setAdapter(gridViewAdapter);

        analysisViewModel.init(getContext());

        intent = new Intent(root.getContext(), CategoryExpensesActivity.class);

    }

    /**
     * This method is responsible for open a new activity (category expenses) when the user
     * click on a category in grid view list
     */
    private void openCategoryExpensesActivity(){
        gridViewAnalysis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendSelectedCategoryInformationToActivity(i);

                startActivity(intent);
            }
        });
    }


    /**
     * A method to send a selected category information to a new activity that opens when the user
     * click on the category in analysis fragment.
     * @param i category position
     */
    private void sendSelectedCategoryInformationToActivity(int i){


        String selectedName = analysisViewModel.getCategories().getValue().get(i).category.get_name();
        double selectedSpent = analysisViewModel.getCategories().getValue().get(i).spent;
        double selectedBudget = analysisViewModel.getCategories().getValue().get(i).limit;
        int selectedCategoryId= analysisViewModel.getCategories().getValue().get(i).category.get_id();
        int selectedCategoryMonth = analysisViewModel.getDate().getValue().getMonth();
        int selectedCategoryYear = analysisViewModel.getDate().getValue().getYear();
        String selectedCategoryColor = analysisViewModel.getCategories().getValue().get(i).category.get_color();



        intent.putExtra("spent",selectedSpent);
        intent.putExtra("budget",selectedBudget);
        intent.putExtra("name",selectedName);
        intent.putExtra("id",selectedCategoryId);
        intent.putExtra("categoryMonth",selectedCategoryMonth);
        intent.putExtra("categoryYear",selectedCategoryYear);
        intent.putExtra("categoryColor",selectedCategoryColor);
    }


    /**
     * A method to update categories in grid view.
     */
    private void updateCategories() {
        analysisViewModel.getCategories().observe(this, new Observer<List<CategoryWithMoney>>() {
            @Override
            public void onChanged(List<CategoryWithMoney> categoryWithMonies) {
                gridViewAdapter.clear();
                gridViewAdapter.addAll(categoryWithMonies);

            }
        });
    }


    /**
     * A method to update the big pie chart
     */
    private void updateBigPieChart(){
        analysisViewModel.getTotalAndSpent().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {

                spent = integers.get(0);
                total = integers.get(1);

                budgetText.setText("Your budget : " + total);

                piechart.clear();
                makePie(piechart);
            }
        });
    }


    /**
     * A method to show the total budget in view
     */
    private void showTotalBudget(){
        budgetText.setText("Your budget : " + total);
    }


    /**
     * A method to update the date
     */
    private void updateDate(){
        analysisViewModel.getDate().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                showDate(date);
            }
        });
    }


    /**
     * A method to show the date in view
     * @param date the date that will be displayed
     */
    private void showDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        dateText.setText(sdf.format(date) + "-01");
    }


    /**
     * A method to update view when the user click on left arrow (previous month)
     */
    private void updateViewWhenClickOnLeftArrow(){
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysisViewModel.leftArrowClick();
            }
        });
    }


    /**
     * A method to update view when the user click on right arrow (next month)
     */
    private void updateViewWhenClickOnRightArrow(){
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysisViewModel.rightArrowClick();
            }
        });
    }

    /**
     * The methods make the circle diagram (Pie chart) and sets the data and the color for it
     * @param piechart the piechart view object
     */

    private void makePie(PieChart piechart){
        piechart.setUsePercentValues(true);

        piechart.setHoleRadius(80f);
        piechart.setTransparentCircleRadius(80f);

        double precent = (spent/total) * 100;
        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry( (float)precent,"Spent"));
        value.add(new PieEntry((float)(100-precent),"Left"));

        PieDataSet dataSet = new PieDataSet(value,null);


        PieData pieData = new PieData(dataSet);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(228, 44, 100));
        colors.add(Color.rgb(203, 204, 196));

        //colors.add(getColorByName(analysisViewModel.getCategories().getValue().get(i).category.get_color()));
        //colors.add(Color.rgb(203, 204, 196));





        dataSet.setColors(colors);
        dataSet.setValueTextSize(0);
        piechart.setData(pieData);

        piechart.setCenterText(spent + " Kr");
        piechart.setCenterTextColor(Color.rgb(255, 255, 255));
        piechart.setHoleColor(Color.rgb(40, 43, 51));
        piechart.setDescription(null);


        piechart.setDrawEntryLabels(false);
        piechart.setCenterTextSize(18);

        piechart.getLegend().setEnabled(false);





       // piechart.setCenterText(analysisViewModel.getTotalAndSpent().getValue().get());
        //Log.i("PieInfo", value.get(0).getValue() + "");

    }



   /* public int getColorByName(String name) {
        int colorId = 0;

        try {
            Class res = R.color.class;
            Field field = res.getField(name);
            colorId = field.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return colorId;
    }*/

}