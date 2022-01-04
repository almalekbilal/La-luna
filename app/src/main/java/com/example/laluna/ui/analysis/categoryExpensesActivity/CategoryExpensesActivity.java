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

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.Category;
import com.example.laluna.Model.CategoryWithExpenses;
import com.example.laluna.Model.Expense;
import com.example.laluna.Model.circleDiagrams.CategoryCircleDiagram;
import com.example.laluna.R;
import com.example.laluna.ui.PieChartMaker;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This class is responsible for showing information about the category and its expenses to the user
 * @author Ali Malla
 */
public class CategoryExpensesActivity extends AppCompatActivity {



    private PieChart clickedCategoryPieCh;   // category pie chart to show spent and left money
    private TextView categoryNameAndBudget;
    private TextView categorySpent;
    private ImageButton backButton;
    private ListView categoryExpensesListView;

    private Intent intent;



    /**
     * This method show all things in the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        if(intent.getExtras() != null){
            showAllCategoryInformation();
        }

    }


    /**
     * This method initializes most of the class's attributes
     */
    private void init(){
        setContentView(R.layout.activity_category_expenses);


        clickedCategoryPieCh = findViewById(R.id.clickedCategoryPieCh);
        categoryNameAndBudget = findViewById(R.id.categoryNameAndBudget);
        categorySpent = findViewById(R.id.categorySpent);
        backButton = findViewById(R.id.backButton);
        categoryExpensesListView = findViewById(R.id.categoryExpensesListView);

        intent = getIntent();

    }


    /**
     * this method gets all the items that will shows in the activity
     */
    private void showAllCategoryInformation(){
        Bundle data = intent.getExtras();
        Category cat = (Category)data.getSerializable("category");


        ArrayList<Expense> expenses = (ArrayList<Expense>) data.getSerializable("expenses");
        int budget = data.getInt("budget");

        CategoryWithExpenses categoryWithExpenses = new CategoryWithExpenses(cat, expenses, budget);



        PieChartMaker.makePie(clickedCategoryPieCh, new CategoryCircleDiagram(categoryWithExpenses));

        ListViewAdapter listAdapter = new ListViewAdapter(categoryWithExpenses.getCategoryExpenses(), this);

        categoryExpensesListView.setAdapter(listAdapter);


        TextView spent = findViewById(R.id.categorySpent);
        TextView budgetView = findViewById(R.id.categoryNameAndBudget);

        spent.setText(Arithmetic.calculateTotalMoneySpent(expenses) + " kr");
        budgetView.setText(cat.get_name() + " budget " + budget + " kr");
        backButtonToAnalysisFragment();



    }





    /**
     * This method is responsible to back from activity to Analysis fragment when the user click on
     * back button
     */
    private void backButtonToAnalysisFragment(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}