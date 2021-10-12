package com.example.laluna;

import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryExpensesActivity extends AppCompatActivity {


    private double spent = 150;
    private double budget = 300;


    private PieChart clickedCategoryPieCh;
    private TextView categoryNameAndBudget;
    private TextView categorySpent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_expenses);



        clickedCategoryPieCh = findViewById(R.id.clickedCategoryPieCh);
        categoryNameAndBudget = findViewById(R.id.categoryNameAndBudget);
        categorySpent = findViewById(R.id.categorySpent);


        Intent intent = getIntent();

        if(intent.getExtras() != null){


            String selectedCategoryName = intent.getStringExtra("name");

            clickedCategoryPieCh.setCenterText(selectedCategoryName);
            spent = intent.getDoubleExtra("spent",0);
            budget = intent.getDoubleExtra("budget",0);



            String budgetToString = Double.toString(budget);
            String spentToString = Double.toString(spent);
            categoryNameAndBudget.setText(selectedCategoryName.toUpperCase(Locale.ROOT) + " " + "BUDGET" + " " + budgetToString + " KR");

            categorySpent.setText(spentToString + " KR");

            makeCategoryPieCh(clickedCategoryPieCh);
        }

    }





    private void makeCategoryPieCh(PieChart piechart) {
        piechart.setUsePercentValues(true);

        piechart.setHoleRadius(85f);
        piechart.setTransparentCircleRadius(85f);

        double precent = (spent/budget) * 100;
        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry( (float)precent,"Spent"));
        value.add(new PieEntry((float)(100-precent),"Left"));

        PieDataSet dataSet = new PieDataSet(value,null);


        PieData pieData = new PieData(dataSet);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(3, 169, 241));
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