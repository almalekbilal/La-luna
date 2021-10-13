package com.example.laluna.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    Button addCategoryButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoriesViewModel =
                ViewModelProviders.of(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);

         final CategoriesAdapter categoryAdapter= new CategoriesAdapter(getContext(), categoryList);
        ListView categoryListView = (ListView)   root.findViewById(R.id.categoryListView);
        categoryListView.setAdapter(categoryAdapter);

        categoriesViewModel.init(getContext());

        categoryListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getContext(), CategoriesEditActivity.class);
                        startActivity(intent);
                        Category category = (Category) adapterView.getItemAtPosition(i);
                        Toast.makeText(getContext() ,category.get_name(), Toast.LENGTH_LONG).show();

                    }
                }

        );
        addCategoryButton = (Button) root.findViewById(R.id.addCategoryButton);

        addButtonClicked();


        categoriesViewModel.getCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.clear();
                categoryAdapter.addAll(categories);





            }
        });

        return root;


    }
    private void addButtonClicked(){

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoriesAddActivity.class);
                startActivity(intent);
            }
        });



    }




}
