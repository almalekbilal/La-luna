package com.example.laluna.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.Model.Category;
import com.example.laluna.R;

import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment {


    private List<Category> categoryList = new ArrayList<>();
    private CategoriesViewModel categoriesViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoriesViewModel =
                ViewModelProviders.of(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);

         final CategoriesAdapter categoryAdapter= new CategoriesAdapter(getContext(), categoryList);
        ListView categoryListView = (ListView)   root.findViewById(R.id.categoryListView);
        categoryListView.setAdapter(categoryAdapter);

        categoriesViewModel.init(getContext());


        categoriesViewModel.getCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.clear();
                categoryAdapter.addAll(categories);




            }
        });

        return root;
    }
}
