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

/**
 * This class is responsible for showing information about the page where the user can add a new category.
 *
 * @author Ali Alkhaled
 */

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
        initComponents();





            addCategoryCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  finish();

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
                         Toast.makeText(getBaseContext(), "Category is added!", Toast.LENGTH_LONG).show();

                         finish();
                     }

                }


            });

    }

    private void initComponents() {

        addCategoryName = findViewById(R.id.addCategoryName);
        addCategoryBudget = findViewById(R.id.addCategoryBudget);
        addCategoryAdd = findViewById(R.id.addCategoryAdd);
        addCategoryCancel = findViewById(R.id.addCategoryCancel);
    }
}
