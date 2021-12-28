package com.example.laluna.ui;

import android.graphics.Color;

import com.example.laluna.Model.circleDiagrams.CircleDiagram;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PieChartMaker {

    public static void makePie(PieChart piechart, CircleDiagram diagram){
        piechart.setUsePercentValues(true);

        piechart.setHoleRadius(80f);
        piechart.setTransparentCircleRadius(80f);

        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry( diagram.getSpentRate(),"Spent"));
        value.add(new PieEntry( diagram.getTotalRate(),"Left"));

        PieDataSet dataSet = new PieDataSet(value,null);


        PieData pieData = new PieData(dataSet);


        dataSet.setColors(diagram.getColors());
        dataSet.setValueTextSize(0);
        piechart.setData(pieData);

        piechart.setCenterText(diagram.getCenterText());
        piechart.setCenterTextColor(Color.rgb(255, 255, 255));
        piechart.setHoleColor(Color.rgb(40, 43, 51));
        piechart.setDescription(null);


        piechart.setDrawEntryLabels(false);
        piechart.setCenterTextSize(18);

        piechart.getLegend().setEnabled(false);

    }
}
