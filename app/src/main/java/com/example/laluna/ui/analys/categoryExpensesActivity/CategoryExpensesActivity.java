package com.example.laluna.ui.analys.categoryExpensesActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.example.laluna.ui.analys.AnalysisViewModel;
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

    private AnalysisViewModel analysisViewModel;

    private PieChart clickedCategoryPieCh;
    private TextView categoryNameAndBudget;
    private TextView categorySpent;
    private ImageButton backButton;
    private ListView categoryExpensesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_expenses);
        analysisViewModel =
                ViewModelProviders.of(this).get(AnalysisViewModel.class);

        analysisViewModel.init(this);

        clickedCategoryPieCh = findViewById(R.id.clickedCategoryPieCh);
        categoryNameAndBudget = findViewById(R.id.categoryNameAndBudget);
        categorySpent = findViewById(R.id.categorySpent);

        backButton = findViewById(R.id.backButton);
        categoryExpensesListView = findViewById(R.id.categoryExpensesListView);



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


            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_analysis,new Fragment()).commit();

                }
            });




            final int id = intent.getIntExtra("id",0);
            int year = intent.getIntExtra("categoryYear",5);
            int month = intent.getIntExtra("categoryMonth",9);




            final ListViewAdapter listAdapter = new ListViewAdapter
                    (new ArrayList<Expense>(), this,id);

            categoryExpensesListView.setAdapter(listAdapter);

            analysisViewModel.updateCategoryExpenses(id,year,month);

            analysisViewModel.getCategoryExpenses().observe(this, new Observer<List<Expense>>() {
                @Override
                public void onChanged(List<Expense> expenses) {


                    listAdapter.clear();
                    listAdapter.addAll(expenses);

                }
            });

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