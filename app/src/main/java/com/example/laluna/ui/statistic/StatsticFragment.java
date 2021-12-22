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

import com.example.laluna.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatsticFragment extends Fragment {

    private StatsticViewModel statsticViewModel;
    BarChart barChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsticViewModel =
                ViewModelProviders.of(this).get(StatsticViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statstic, container, false);
        barChart = (BarChart) root.findViewById(R.id.bargraph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44,3));
        barEntries.add(new BarEntry(60,1));
        barEntries.add(new BarEntry(20,2));
        barEntries.add(new BarEntry(22,3));
        barEntries.add(new BarEntry(52,4));
        barEntries.add(new BarEntry(33,5));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Spending");

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setTouchEnabled(true);
        barChart.setBackgroundColor(Color.WHITE);

        return root;
    }


    public ArrayList <String> getList (Calendar startDate, Calendar endDate) {
        ArrayList<String> list = new ArrayList<>();
        while (startDate.compareTo(endDate) <= 0){
            list.add(getDate (startDate) );
            startDate.add(Calendar.MONTH,1);


        }

        return list;
    }

    public String getDate(Calendar calender){
        String curDate = calender.get(Calendar.YEAR) + "/"+
                (calender.get(Calendar.MONTH) +1)+ "/" + calender.get(Calendar.DAY_OF_MONTH);
        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return curDate;

    }


}









/*final TextView textView = root.findViewById(R.id.text_help);
        statsticViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        }); */