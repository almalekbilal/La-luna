package com.example.laluna.ui.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.Model.average.times.TimeFactory;
import com.example.laluna.Model.average.times.TimeObject;
import com.example.laluna.Model.repository.ExpenseRepository;
import com.example.laluna.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatsticFragment extends Fragment {

    ExpenseRepository expenseRepository;
    private StatsticViewModel statsticViewModel;
    List<TimeObject> dataArray = new ArrayList<>();
    double averageValue;
    BarChart barChart;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> barLabelNames;
    List<TimeObject> timeObjectsList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsticViewModel =
                ViewModelProviders.of(this).get(StatsticViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statstic, container, false);
        barChart = (BarChart) root.findViewById(R.id.bargraph);
        expenseRepository = ExpenseRepository.getInstance(root.getContext());
        timeObjectsList=new ArrayList<>();
        barEntries = new ArrayList<>();
        barLabelNames = new ArrayList<>();
        averageValue = 0;


        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),44));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),22));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),11));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),23));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),12));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),13));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),21));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),12));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),2));

        statsticViewModel.getAverageData().observe(this, new Observer<Double>() {

            @Override
            public void onChanged(Double average) {
                averageValue = average;
                barChartSettings();

            }
        });




        monthOnClick();
        statsticViewModel.getTimeData().observe(this, new Observer<List<TimeObject>>() {
            @Override
            public void onChanged(List<TimeObject> timeObjects) {

                timeObjectsList =timeObjects;
                drawBarChart(timeObjectsList);
                barChartSettings();

            }
        });

        barChartSettings();

        Button dayButton = root.findViewById(R.id.dayButton);
        Button weekButton = root.findViewById(R.id.weekButton);
        Button monthButton = root.findViewById(R.id.monthButton);

        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dayOnClick();
            }
        });
        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weekOnClick();
            }
        });
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monthOnClick();
            }
        });


        return root;
    }


    public void drawBarChart( List<TimeObject> timeObjects){

        barEntries.clear();
        barLabelNames.clear();
        for (int i = timeObjects.size()-1; i>= 0; i--){
            String label = timeObjects.get(i).toString();
            System.out.println(label);
            int value = (int)timeObjects.get(i).getValue();
            barEntries.add(new BarEntry(timeObjects.size()-i-1,value));
            barLabelNames.add(label);
        }


        BarDataSet barDataSet = new BarDataSet(barEntries, "Spending");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setBarBorderColor(Color.rgb(203, 203, 203));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barLabelNames));
        xAxis.setLabelCount(barLabelNames.size());


        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.7f);

        barChart.setData(barData);
        barChart.invalidate();

    }


    private void barChartSettings(){

        barChart.setDrawBorders(true);
        barChart.setBorderColor(Color.RED);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.animateY(500);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.invalidate();

    }

    public void dayOnClick() {

                Calendar end = Calendar.getInstance();
                Calendar start = Calendar.getInstance();

        start.add(Calendar.DAY_OF_MONTH, -14);
                statsticViewModel.getDayData(start, end, expenseRepository);
    }

    public void weekOnClick() {
               Calendar end = Calendar.getInstance();
              Calendar start = Calendar.getInstance();

                start.add(Calendar.WEEK_OF_YEAR, -12);
                statsticViewModel.getWeekData(start, end, expenseRepository);
            }




    public void monthOnClick() {
                Calendar end = Calendar.getInstance();
                Calendar start = Calendar.getInstance();

                start.add(Calendar.MONTH, -12);
                    statsticViewModel.getMonthData(start, end, expenseRepository);

            }

}