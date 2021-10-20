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
    private EditText editTextName, editTextLimit;
    private Button buttonSave, buttonCancel, buttonDelete;
    private ImageButton imageButtonEdit;
    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        viewModel.init(this);

        initComponents();
        intent = getIntent();

        setDefaultNameForEditTexts();


        final String oldName = editTextName.getText().toString();
        final int oldLimit = Integer.parseInt(editTextLimit.getText().toString());



        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCategory();
                Toast.makeText(getBaseContext(), "The category is deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEditCategory();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSaveChanges(oldName, oldLimit);
                finish();
            }
        });

    }

    private void initComponents () {

        editTextName = findViewById(R.id.editCategoryName);
        editTextLimit = findViewById(R.id.editCategoryBudget);
        buttonSave = findViewById(R.id.editCategorySave);
        buttonCancel = findViewById(R.id.editCategoryCancel);
        imageButtonEdit = findViewById(R.id.editCategoryImageButton);
        buttonDelete = findViewById(R.id.editCategoryDelete);
    }


    private void deleteCategory() {

        int id = intent.getIntExtra("categoryId", 0);
        viewModel.deleteCategory(id);
    }

    private void updateSaveChanges(String oldName, int oldLimit){

        String newName = editTextName.getText().toString();
        int newLimit = Integer.parseInt(editTextLimit.getText().toString());

        int id =  intent.getIntExtra("categoryId", 0);
        String color = intent.getStringExtra("categoryColor");
        String date = intent.getStringExtra("categoryDate");
        int picture = intent.getIntExtra("categoryPicture",0);

        viewModel.editCategory(newName,id,newLimit,date,picture,color);

        if (!oldName.equals(newName) || newLimit != oldLimit) {
            Toast.makeText(getBaseContext(),"The category has been edited", Toast.LENGTH_LONG).show();
        }

    }

    private void enableEditCategory() {
        if (editTextName.isEnabled() && editTextLimit.isEnabled()) {
            editTextName.setEnabled(false);
            editTextLimit.setEnabled(false);
        }
        else {
            editTextName.setEnabled(true);
            editTextLimit.setEnabled(true);
        }

    }

    private void setDefaultNameForEditTexts() {
        String selectedCategoryName = intent.getStringExtra("categoryName");
        String selectedCategoryLimit = Integer.toString(intent.getIntExtra("categoryBudget", 1));

        editTextName.setText(selectedCategoryName);
        editTextLimit.setText(selectedCategoryLimit);
    }

}