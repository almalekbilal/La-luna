package com.example.laluna.View.ui.analys;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class AnalysisFragment extends Fragment {

    private AnalysisViewModel analysisViewModel;
    private double total = 300, spent= 150;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analysisViewModel =
                ViewModelProviders.of(this).get(AnalysisViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_analysis, container, false);

        final GridView gridViewAnalysis = root.findViewById(R.id.gridViewAnalysis);


        final GridViewAdapter gridViewAdapter = new GridViewAdapter(analysisViewModel.names, analysisViewModel.images,
                root.getContext());
        gridViewAnalysis.setAdapter(gridViewAdapter);

        analysisViewModel.init(getContext());


        final PieChart piechart = root.findViewById(R.id.pc_totalspent);

        makePie(piechart);


        analysisViewModel.getCategories().observe(this, new Observer<List<CategoryWithMoney>>() {
            @Override
            public void onChanged(List<CategoryWithMoney> categoryWithMonies) {

            }
        });




        analysisViewModel.getTotalAndSpent().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {

                spent = integers.get(0);
                total = integers.get(1);

                piechart.clear();
                makePie(piechart);
            }
        });






        return root;
    }


    private void makePie(PieChart piechart){
        piechart.setUsePercentValues(true);

        piechart.setHoleRadius(80f);
        piechart.setTransparentCircleRadius(80f);

        double precent = (spent/total) * 100;
        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry( (float)precent, "Spenderat"));
        value.add(new PieEntry((float)(100-precent) , "Kvar"));

        PieDataSet dataSet = new PieDataSet(value,"MoneySpent");


        PieData pieData = new PieData(dataSet);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(255,0,0));
        colors.add(Color.rgb(0,255,0));


        dataSet.setColors(colors);

        piechart.setData(pieData);

        Log.i("PieInfo", value.get(0).getValue() + "");

    }

}