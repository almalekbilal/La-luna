package com.example.laluna.ui.statistic;

import android.graphics.Color;
import android.os.Bundle;
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
import com.example.laluna.Model.avarage.times.TimeObject;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsticViewModel =
                ViewModelProviders.of(this).get(StatsticViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statstic, container, false);
        barChart = (BarChart) root.findViewById(R.id.bargraph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> barLabelNames = new ArrayList<>();

        expenseRepository = ExpenseRepository.getInstance(root.getContext());

        BarDataSet barDataSet = new BarDataSet(barEntries, "Spending");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setBarBorderColor(Color.rgb(203, 203, 203));

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(2.9f);
        barChart.setData(barData);

        barChart.setDrawBorders(true);
        barChart.setBorderColor(Color.RED);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.invalidate();
        barChart.animateY(500);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barLabelNames));
        xAxis.setLabelCount(barLabelNames.size());

        barChart.invalidate();
        barChart.animateY(500);

        return root;
    }

    public void dayOnClick() {

        statsticViewModel.getTimeData().observe(this, new Observer<List<TimeObject>>() {
            @Override
            public void onChanged(List<TimeObject> timeObjects) {

                Calendar start = Calendar.getInstance();
                Calendar end = (Calendar) start.clone();

                end.add(Calendar.DAY_OF_MONTH, -14);
                statsticViewModel.getDayData(start, end, expenseRepository);
                dataArray = timeObjects;


            }
        });


    }

    public void weekOnClick() {
        statsticViewModel.getTimeData().observe(this, new Observer<List<TimeObject>>() {
            @Override
            public void onChanged(List<TimeObject> timeObjects) {

                Calendar start = Calendar.getInstance();
                Calendar end = (Calendar) start.clone();

                end.add(Calendar.WEEK_OF_YEAR, -12);


                statsticViewModel.getWeekData(start, end, expenseRepository);
                dataArray = timeObjects;

            }
        });

    }

    public void monthOnClick() {
        statsticViewModel.getTimeData().observe(this, new Observer<List<TimeObject>>() {
            @Override
            public void onChanged(List<TimeObject> timeObjects) {


                Calendar start = Calendar.getInstance();
                Calendar end = (Calendar) start.clone();

                end.add(Calendar.MONTH, -12);

                statsticViewModel.getMonthData(start, end, expenseRepository);
                dataArray = timeObjects;

            }
        });


    }

}




/*
        ArrayList<monthBarGraph> dataArray = new ArrayList<>();
        dataArray.add(new monthBarGraph("Jen",10));
        dataArray.add(new monthBarGraph("feb",20));
        dataArray.add(new monthBarGraph("mar",30));

        dataArray.add(new monthBarGraph("apr",40));
        dataArray.add(new monthBarGraph("may",30));

        dataArray.add(new monthBarGraph("jun",50));
        dataArray.add(new monthBarGraph("jul",60));
        dataArray.add(new monthBarGraph("may",30));
        dataArray.add(new monthBarGraph("sep",70));
        dataArray.add(new monthBarGraph("oct",80));
        dataArray.add(new monthBarGraph("nov",90));
        dataArray.add(new monthBarGraph("des",100));

        for (int i = 0; i< dataArray.size(); i++){
            String month = dataArray.get(i).getMonth();
            int expense = dataArray.get(i).getExpenses();
            barEntries.add(new BarEntry(i,expense));
            barLabelNames.add(month);

        }