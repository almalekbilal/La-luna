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




   // private final List<CategoryWithMoney> categoryWithMoneyList;


    private final String [] name;
  //  private int [] images;
    Context context;
    LayoutInflater layoutInflater;

    public GridViewAdapter(String[] name,Context context) {
        this.name = name;
        //this.images = images;
        this.context = context;
       // this.categoryWithMoneyList = categoryWithMonies;
    }

    @Override
    public int getCount() {
        return name.length;
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


        //ImageView categoryAnalysis = (ImageView) view.findViewById(R.id.categoryAnalysis);
        TextView categorySpentMoney = (TextView) view.findViewById(R.id.categorySpentMoney);

        PieChart pc_categorySpent =(PieChart) view.findViewById(R.id.pc_categorySpent);


      //  categorySpentMoney.setText(categoryWithMoneyList.get(i).spent);
        categorySpentMoney.setText(name[i]);
        //categoryAnalysis.setImageResource(images[i]);

        makeCategoryPie(pc_categorySpent);

        return view;
    }

    private void makeCategoryPie(PieChart piechart) {
        piechart.setUsePercentValues(true);

        piechart.setHoleRadius(80f);
        piechart.setTransparentCircleRadius(80f);

        double precent = (150/300) * 100;
        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry( (float)precent,"Spent"));
        value.add(new PieEntry((float)(100-precent),"Left"));

        PieDataSet dataSet = new PieDataSet(value,null);


        PieData pieData = new PieData(dataSet);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(178, 55, 142));
        colors.add(Color.rgb(203, 204, 196));


        dataSet.setColors(colors);

        piechart.setData(pieData);

        piechart.setCenterText("Car");
        piechart.setCenterTextColor(Color.rgb(255, 255, 255));
        piechart.setHoleColor(Color.rgb(40, 43, 51));
        piechart.setDescription(null);


        piechart.setDrawEntryLabels(false);
    }

}
