package com.example.laluna.ui.categories;

import android.graphics.Color;
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
 * @author Deaa Khankan
 */

public class CategoriesAddActivity extends AppCompatActivity {

    private CategoriesViewModel viewModel;
    private EditText addCategoryName, addCategoryBudget;
    private Button addCategoryAdd, addCategoryCancel;


    /**
     * This method is responsible for showing all components i this activity and update them
     * Through it all data fields and components in this class are initialized, activated and updated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        initComponents();

        onClickAddCategory();
        onClickCancel();


    }

    private void initComponents() {

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        viewModel.init(this);

        addCategoryName = findViewById(R.id.addCategoryName);
        addCategoryBudget = findViewById(R.id.addCategoryBudget);
        addCategoryAdd = findViewById(R.id.addCategoryAdd);
        addCategoryCancel = findViewById(R.id.addCategoryCancel);
    }

    private void onClickAddCategory() {

        addCategoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final boolean areNotFilled = addCategoryName.getText().toString().equals("") ||
                        addCategoryBudget.getText().toString().equals("");
                if (areNotFilled){
                    Toast.makeText(getBaseContext(), "Name and Budget must be filled", Toast.LENGTH_LONG).show();
                }

                else {
                    final String name = addCategoryName.getText().toString();
                    final int limit = Integer.parseInt(addCategoryBudget.getText().toString());

                    viewModel.addCategory(name, limit, R.drawable.food, "#ffff00", new Date());
                    Toast.makeText(getBaseContext(), "Category is added!", Toast.LENGTH_LONG).show();

                    finish();
                }
            }
        });

    }

    private void onClickCancel() {
        addCategoryCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }

}
