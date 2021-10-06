package com.example.laluna.View.ui.analys;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laluna.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {


    private double total = 300, spent= 150;



    private List<CategoryWithMoney> categoryWithMoneyList;



    Context context;
    LayoutInflater layoutInflater;

    public GridViewAdapter(List<CategoryWithMoney> categoryWithMonies,Context context) {

        this.context = context;
        this.categoryWithMoneyList = categoryWithMonies;
    }

    @Override
    public int getCount() {
        return categoryWithMoneyList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(layoutInflater == null) {
            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null){
            view = layoutInflater.inflate(R.layout.customcategoryanalysis_analysis,null);
        }


        TextView categorySpentMoney = (TextView) view.findViewById(R.id.categorySpentMoney);

        PieChart pc_categorySpent =(PieChart) view.findViewById(R.id.pc_categorySpent);

        categorySpentMoney.setText("$"+Integer.toString(categoryWithMoneyList.get(i).spent)+".00");


        //categorySpentMoney.setText(categoryWithMoneyList.get(i).category.get_limit());

        //pc_categorySpent.setCenterText(categoryWithMoneyList.get(i).category.get_name());

        makeCategoryPie(pc_categorySpent);



        return view;
    }

    private void makeCategoryPie(PieChart piechart) {
        piechart.setUsePercentValues(true);

        piechart.setHoleRadius(85f);
        piechart.setTransparentCircleRadius(85f);

        double precent = (spent/total) * 100;
        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry( (float)precent,"Spent"));
        value.add(new PieEntry((float)(100-precent),"Left"));

        PieDataSet dataSet = new PieDataSet(value,null);


        PieData pieData = new PieData(dataSet);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(3, 169, 241));
        colors.add(Color.rgb(203, 204, 196));


        dataSet.setColors(colors);

        piechart.setData(pieData);

        piechart.setCenterText("Car");
        piechart.setCenterTextSize(30);
        piechart.setCenterTextColor(Color.rgb(255, 255, 255));
        piechart.setHoleColor(Color.rgb(40, 43, 51));
        piechart.setDescription(null);

        piechart.getLegend().setEnabled(false);

        piechart.setDrawEntryLabels(false);
    }

}
