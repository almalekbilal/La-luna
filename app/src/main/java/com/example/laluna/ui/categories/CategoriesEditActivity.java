package com.example.laluna.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.laluna.R;


public class CategoriesEditActivity extends AppCompatActivity {

    private CategoriesViewModel viewModel;
    private EditText editCategoryName, editCategoryBudget;
    private Button editCategorySave, editCategoryCancel;
    private ImageButton editCategoryImageButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        viewModel.init(this);

        editCategoryName = findViewById(R.id.editCategoryName);
        editCategoryBudget = findViewById(R.id.editCategoryBudget);
        editCategorySave = findViewById(R.id.editCategorySave);
        editCategoryCancel = findViewById(R.id.editCategoryCancel);
        editCategoryImageButton = findViewById(R.id.editCategoryImageButton);


        final Intent intent = getIntent();

        if (intent.getExtras() != null) {
            editCategoryName.setText(intent.getStringExtra("categoryName"));
            int selectedCategoryIntBudget = intent.getIntExtra("categoryBudget", 1);

            editCategoryName.setEnabled(false);
            editCategoryBudget.setEnabled(false);
            editCategoryBudget.setText(Integer.toString(selectedCategoryIntBudget));
        }

            intent.getIntExtra("categoryId", 0);

            editCategoryImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editCategoryName.setEnabled(true);
                    editCategoryBudget.setEnabled(true);

                }
            });


            editCategorySave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newName = editCategoryName.getText().toString();
                    int newLimit = Integer.parseInt(editCategoryBudget.getText().toString());
                    viewModel.editCategory("test", 25, (intent.getIntExtra("categoryId", 0)));
                }


            });


    }
}