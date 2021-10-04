package com.example.laluna.View.ui.analys;

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

import java.util.List;

public class AnalysFragment extends Fragment {

    private AnalysViewModel analysViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analysViewModel =
                ViewModelProviders.of(this).get(AnalysViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        analysViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        analysViewModel.getCategories().observe(this, new Observer<List<CategoryWithMoney>>() {
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
        });
        return root;
    }
}