package com.example.laluna.View.ui.analys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;

import java.util.List;

public class AnalysFragment extends Fragment {

    private AnalysViewModel analysViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analysViewModel =
                ViewModelProviders.of(this).get(AnalysViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_analysis, container, false);

        final GridView gridViewAnalysis = root.findViewById(R.id.gridViewAnalysis);


        analysViewModel.getText().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                GridViewAdapter gridViewAdapter = new GridViewAdapter(strings,analysViewModel.images,
                        root.getContext());
                gridViewAnalysis.setAdapter(gridViewAdapter);
            }
        });


        /*analysViewModel.getCategories().observe(this, new Observer<List<CategoryWithMoney>>() {
            @Override
            public void onChanged(List<CategoryWithMoney> categoryWithMonies) {

            }
        });

        analysViewModel.getMoneySpent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });

        analysViewModel.getTotalBudget().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });*/
        return root;
    }
}