package com.example.laluna.ui.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.Model.Category;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatsticFragment extends Fragment {

    ExpenseRepository expenseRepository;
    private StatsticViewModel statsticViewModel;
    List<TimeObject> dataArray = new ArrayList<>();
    double average;
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



        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),44));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),22));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),11));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),23));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),12));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),13));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),21));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),12));
        timeObjectsList.add(TimeFactory.getMonthObject(Calendar.getInstance(),2));




        weekOnClick();
        statsticViewModel.getTimeData().observe(this, new Observer<List<TimeObject>>() {
            @Override
            public void onChanged(List<TimeObject> timeObjects) {
                timeObjectsList =timeObjects;
                drawBarChart(timeObjects);

            }
        });

        barChartSettings();


        return root;
    }


    public void drawBarChart( List<TimeObject> timeObjects){

        String label = "";
        for (int i = 0; i< timeObjects.size(); i++){
            label = timeObjects.get(i).toString();
            int value = (int)timeObjects.get(i).getValue();
            barEntries.add(new BarEntry(i,value));
            barLabelNames.add(label);
        }


        BarDataSet barDataSet = new BarDataSet(barEntries, "Spending");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setBarBorderColor(Color.rgb(203, 203, 203));

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.7f);
        barChart.setData(barData);
        barChart.invalidate();

    }


    private void barChartSettings(){

        barChart.setDrawBorders(true);
        barChart.setBorderColor(Color.RED);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.animateY(500);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barLabelNames));
        xAxis.setLabelCount(barLabelNames.size());

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