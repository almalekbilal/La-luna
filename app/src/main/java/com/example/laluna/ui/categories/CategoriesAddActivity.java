package com.example.laluna.ui.categories;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;

import java.util.Date;



public class CategoriesAddActivity extends AppCompatActivity {

    private CategoriesViewModel viewModel;
    private EditText addCategoryName, addCategoryBudget;
    private Button addCategoryAdd, addCategoryCancel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);


        viewModel.init(this);



        addCategoryName = findViewById(R.id.addCategoryName);
        addCategoryBudget = findViewById(R.id.addCategoryBudget);
        addCategoryAdd = findViewById(R.id.addCategoryAdd);
        addCategoryCancel = findViewById(R.id.addCategoryCancel);



            addCategoryCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //.......TODO

                }
            });


            addCategoryAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     if(!addCategoryBudget.getText().toString().equals(null) &&
                           !addCategoryBudget.getText().toString().equals(null)) {
                         String name = addCategoryName.getText().toString();
                         int limit = Integer.parseInt(addCategoryBudget.getText().toString());


                         viewModel.addCategory(name, limit, R.drawable.food, null, new Date());
                         Toast.makeText(getBaseContext(), "hi", Toast.LENGTH_LONG).show();

                         CategoriesFragment categoriesFragment = new CategoriesFragment();

                         getSupportFragmentManager().beginTransaction().
                                 replace(R.id.navigation_categories, categoriesFragment).commit();
                     }

                }


            });

    }
}
