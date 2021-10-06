package com.example.laluna.View.ui.analys;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalysisFragment extends Fragment {

    private AnalysisViewModel analysisViewModel;
    private GridViewAdapter gridViewAdapter;
    private double total = 300, spent= 150;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analysisViewModel =
                ViewModelProviders.of(this).get(AnalysisViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_analysis, container, false);

        final GridView gridViewAnalysis = root.findViewById(R.id.gridViewAnalysis);

        final TextView dateText = root.findViewById(R.id.txv_date);


        gridViewAdapter = new GridViewAdapter(new ArrayList<CategoryWithMoney>(),root.getContext());


        gridViewAnalysis.setAdapter(gridViewAdapter);

        analysisViewModel.init(getContext());


        final PieChart piechart = root.findViewById(R.id.pc_categorySpent);

        makePie(piechart);




        analysisViewModel.getCategories().observe(this, new Observer<List<CategoryWithMoney>>() {
            @Override
            public void onChanged(List<CategoryWithMoney> categoryWithMonies) {
                gridViewAdapter.clear();
                gridViewAdapter.addAll(categoryWithMonies);

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

        analysisViewModel.getDate().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateText.setText(sdf.format(date));
            }
        });

        TextView left = root.findViewById(R.id.txv_leftArrow);
        TextView right = root.findViewById(R.id.txv_rightArrow);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysisViewModel.leftArrowClick();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysisViewModel.rightArrowClick();
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
        value.add(new PieEntry( (float)precent,"Spent"));
        value.add(new PieEntry((float)(100-precent),"Left"));

        PieDataSet dataSet = new PieDataSet(value,null);


        PieData pieData = new PieData(dataSet);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(228, 44, 100));
        colors.add(Color.rgb(203, 204, 196));


        dataSet.setColors(colors);
        dataSet.setValueTextSize(0);
        piechart.setData(pieData);

        piechart.setCenterText(spent + " Kr");
        piechart.setCenterTextColor(Color.rgb(255, 255, 255));
        piechart.setHoleColor(Color.rgb(40, 43, 51));
        piechart.setDescription(null);


        piechart.setDrawEntryLabels(false);
        piechart.setCenterTextSize(18);

        piechart.getLegend().setEnabled(false);





       // piechart.setCenterText(analysisViewModel.getTotalAndSpent().getValue().get());
        //Log.i("PieInfo", value.get(0).getValue() + "");

    }

}