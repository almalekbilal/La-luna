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
        ArrayList<String> barLabelNames = new ArrayList<>();


        ArrayList<monthBarGraph> dataArray = new ArrayList<>();
        dataArray.add(new monthBarGraph("Jen",10));
        dataArray.add(new monthBarGraph("feb",20));
        dataArray.add(new monthBarGraph("mar",30));

        dataArray.add(new monthBarGraph("apr",40));
        dataArray.add(new monthBarGraph("may",30));

        dataArray.add(new monthBarGraph("jun",50));
        dataArray.add(new monthBarGraph("jul",60));
        dataArray.add(new monthBarGraph("aug",30));
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

        BarDataSet barDataSet = new BarDataSet(barEntries,"Spending");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setBarBorderColor(Color.rgb(203,203,203));



        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);
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