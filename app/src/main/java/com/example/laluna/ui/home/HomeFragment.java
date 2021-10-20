package com.example.laluna.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laluna.Model.Expense;
import com.example.laluna.R;
import com.example.laluna.ui.addudateexpense.AddExpenseActivity;
import com.example.laluna.ui.addudateexpense.UpdateExpenseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * View class that is responsible for showing the information to the user
 * It communicates the HomeViewModel
 *
 *   @auther (Deaa Khankan)
 *   @auther (Ali Al Khaled)
 */
public class HomeFragment extends Fragment implements ExpensesAdapter.onExpenseClickListener {

    private HomeViewModel homeViewModel;
    private final ArrayList<Expense> expenseArrayList = new ArrayList<Expense>();
    private double total = 300, spent= 150;

    /**
     * This method creates the view and handle the widgets in it
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);


        final PieChart piechart = root.findViewById(R.id.pc_categorySpent);

        makePie(piechart);

        RecyclerView recyclerView = root.findViewById(R.id.categoryListView);
        final TextView budgetText = root.findViewById(R.id.txv_budgetHome);




        final ExpensesAdapter adapter =new ExpensesAdapter(getContext(), expenseArrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        homeViewModel.init(getContext());

        homeViewModel.getTotalAndSpent().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                spent = integers.get(0);
                total = integers.get(1);

                budgetText.setText("Your budget : " + total);
                piechart.clear();
                makePie(piechart);
            }
        });

        homeViewModel.getExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {

                    expenseArrayList.addAll(expenses);
                    adapter.notifyDataSetChanged();
                }
        });


        Button add = root.findViewById(R.id.btn_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(root.getContext(), AddExpenseActivity.class);
               startActivity(intent);
            }
        });





        return root;
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

    @Override
    public void onExpenseClick(Expense expense) {
        Toast.makeText(getContext(),expense.get_name(),Toast.LENGTH_LONG).show();
        Intent i = new Intent(getContext(), UpdateExpenseActivity.class);
        i.putExtra("expense", expense);

        startActivity(i);
    }
}