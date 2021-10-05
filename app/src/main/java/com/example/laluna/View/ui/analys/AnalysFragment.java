package com.example.laluna.View.ui.analys;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AnalysFragment extends Fragment {

    private AnalysViewModel analysViewModel;
    private double total = 300, spent= 150;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        analysViewModel =
                ViewModelProviders.of(this).get(AnalysViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        analysViewModel.init(getContext());


        final PieChart piechart = root.findViewById(R.id.pc_totalspent);

        makePie(piechart);

        analysViewModel.getCategories().observe(this, new Observer<List<CategoryWithMoney>>() {
            @Override
            public void onChanged(List<CategoryWithMoney> categoryWithMonies) {

            }
        });

         analysViewModel.getTotalAndSpent().observe(this, new Observer<List<Integer>>() {
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