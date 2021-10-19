package com.example.laluna.ui.analysis.categoryExpensesActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laluna.Model.Expense;
import com.example.laluna.R;
import com.example.laluna.ui.analysis.AnalysisViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryExpensesActivity extends AppCompatActivity {


    private double spent;
    private double budget;

    private AnalysisViewModel analysisViewModel;

    private PieChart clickedCategoryPieCh;
    private TextView categoryNameAndBudget;
    private TextView categorySpent;
    private ImageButton backButton;
    private ListView categoryExpensesListView;

    private Intent intent;

    private int categoryId;
    private int categoryYear;
    private int categoryMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        if(intent.getExtras() != null){
            showAllCategoryInformation();
        }

    }



    private void showAllCategoryInformation(){
        spent = getSpent();
        budget = getBudget();
        showCategoryNameAndBudget();
        showCategorySpentMoney();
        makeCategoryPieCh(clickedCategoryPieCh);
        backButtonToAnalysisFragment();


        categoryId = getSelectedCategoryId();
        categoryYear = getSelectedCategoryYear();
        categoryMonth = getSelectedCategoryMonth();

        getCategoryExpenses(categoryId, categoryYear, categoryMonth);

        showCategoryExpensesInListView();
    }


    private void init(){
        setContentView(R.layout.activity_category_expenses);

        analysisViewModel = ViewModelProviders.of(this).get(AnalysisViewModel.class);
        analysisViewModel.init(this);

        clickedCategoryPieCh = findViewById(R.id.clickedCategoryPieCh);
        categoryNameAndBudget = findViewById(R.id.categoryNameAndBudget);
        categorySpent = findViewById(R.id.categorySpent);
        backButton = findViewById(R.id.backButton);
        categoryExpensesListView = findViewById(R.id.categoryExpensesListView);

        intent = getIntent();

    }

    private void showCategoryNameAndBudget(){
        String selectedCategoryName = intent.getStringExtra("name");
        clickedCategoryPieCh.setCenterText(selectedCategoryName);   //Category name in pie chart
        String budgetToString = Double.toString(budget);
        categoryNameAndBudget.setText(selectedCategoryName.toUpperCase(Locale.ROOT) + " " + "BUDGET" + " " + budgetToString + " KR");
    }


    private double getSpent(){
        return intent.getDoubleExtra("spent",0);
    }

    private double getBudget(){
        return intent.getDoubleExtra("budget",0);
    }


    private void showCategorySpentMoney(){
        String spentToString = Double.toString(getSpent());
        categorySpent.setText(spentToString + " KR");
    }


    private void backButtonToAnalysisFragment(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private int getSelectedCategoryId(){
        return intent.getIntExtra("id",0);
    }

    private int getSelectedCategoryYear(){
        return intent.getIntExtra("categoryYear",5);
    }

    private int getSelectedCategoryMonth(){
        return intent.getIntExtra("categoryMonth",9);
    }


    private void getCategoryExpenses(int categoryId, int categoryYear, int categoryMonth){
        analysisViewModel.updateCategoryExpenses(categoryId, categoryYear, categoryMonth);
    }


    private void showCategoryExpensesInListView(){
        final ListViewAdapter listAdapter = new ListViewAdapter
                (new ArrayList<Expense>(), this, categoryId);

        categoryExpensesListView.setAdapter(listAdapter);


        analysisViewModel.getCategoryExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {


                listAdapter.clear();
                listAdapter.addAll(expenses);

            }
        });

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