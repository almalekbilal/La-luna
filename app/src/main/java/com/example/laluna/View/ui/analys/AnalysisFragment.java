package com.example.laluna.View.ui.analys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;

public class AnalysisFragment extends Fragment {

    private AnalysisViewModel analysisViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analysisViewModel =
                ViewModelProviders.of(this).get(AnalysisViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_analysis, container, false);

        final GridView gridViewAnalysis = root.findViewById(R.id.gridViewAnalysis);


        analysisViewModel.getText().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                GridViewAdapter gridViewAdapter = new GridViewAdapter(strings, analysisViewModel.images,
                        root.getContext());
                gridViewAnalysis.setAdapter(gridViewAdapter);
            }
        });


        /*analysisViewModel.getCategories().observe(this, new Observer<List<CategoryWithMoney>>() {
            @Override
            public void onChanged(List<CategoryWithMoney> categoryWithMonies) {

            }
        });

        analysisViewModel.getMoneySpent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });

        analysisViewModel.getTotalBudget().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });*/
        return root;
    }
}