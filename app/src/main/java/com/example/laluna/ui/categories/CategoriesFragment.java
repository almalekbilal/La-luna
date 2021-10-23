package com.example.laluna.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.Model.Category;
import com.example.laluna.R;

import java.util.ArrayList;
import java.util.List;


/**
 * View class that is responsible for showing the information to the user
 * It communicates with CategoriesViewModel
 *
 * @author Ali ALkhaled
 * @author Deaa Khankan
 */

public class CategoriesFragment extends Fragment {


    private CategoriesViewModel categoriesViewModel;
    private CategoriesAdapter categoryAdapter;

    private View root;

    private Button addCategoryButton;
    private ListView categoryListView;


    /**
     * Updating data directly when resuming to the fragment
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        if(categoriesViewModel != null){
            categoriesViewModel.init(getContext());
        }
    }


    /**
     * Runs first time to create the view and initiate it
     *
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        initComponents(inflater, container);

        onClickCategoryListView();
        onClickAddButton();

        updateViewOfListView();


        return root;

    }

    private void initComponents(@NonNull LayoutInflater inflater, ViewGroup container) {

        root = inflater.inflate(R.layout.fragment_categories, container, false);
        categoriesViewModel =
                ViewModelProviders.of(this).get(CategoriesViewModel.class);

        categoriesViewModel.init(getContext());


        categoryListView = root.findViewById(R.id.categoryListView);
        addCategoryButton = root.findViewById(R.id.addCategoryButton);

        categoryAdapter = new CategoriesAdapter(getContext(), categoriesViewModel.getCategoryList());
        categoryListView.setAdapter(categoryAdapter);


    }

    private void updateViewOfListView(){

        categoriesViewModel.getCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.clear();
                categoryAdapter.addAll(categories);


            }
        });
    }


    private void onClickAddButton(){

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoriesAddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void onClickCategoryListView() {

        categoryListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    sendCategoryInformationToEditCategory(i);

                }
            });
    }


    private void sendCategoryInformationToEditCategory(int i){

        Intent intent = new Intent(getContext(), CategoriesEditActivity.class);

        String selectedCategoryName = categoriesViewModel.getCategory().getValue().get(i).get_name();
        String selectedCategoryDate = categoriesViewModel.getCategory().getValue().get(i).getCreationDate().toString();
        String selectedCategoryColor= categoriesViewModel.getCategory().getValue().get(i).get_color();

        int selectedCategoryBudget = categoriesViewModel.getCategory().getValue().get(i).get_limit();
        int selectedCategoryId = categoriesViewModel.getCategory().getValue().get(i).get_id();
        int selectedCategoryPicture= categoriesViewModel.getCategory().getValue().get(i).get_pictureName();

        intent.putExtra("categoryName",selectedCategoryName);
        intent.putExtra("categoryBudget",selectedCategoryBudget);
        intent.putExtra("categoryId",selectedCategoryId);
        intent.putExtra("categoryDate",selectedCategoryDate);
        intent.putExtra("categoryPicture",selectedCategoryPicture);
        intent.putExtra("categoryColor",selectedCategoryColor);

        startActivity(intent);
    }




}
