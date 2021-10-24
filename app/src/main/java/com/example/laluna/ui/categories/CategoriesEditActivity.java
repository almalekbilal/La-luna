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

/**
 * This class is responsible for showing information about the page where the user can edit or delete an existing category.
 *
 * @author Ali ALkhaled
 *
 */
public class CategoriesEditActivity extends AppCompatActivity {

    private CategoriesViewModel viewModel;
    private EditText editTextName, editTextLimit;
    private Button buttonSave, buttonCancel, buttonDelete;
    private ImageButton imageButtonEdit;
    private Intent intent;
    private String oldNameEditText;
    private int oldLimitEditText;


    /**
     * This method is responsible for initializing and showing all components i this activity once the class runs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        initComponents();

    }

    /**
     * This method is responsible for updating the view when the user clicks or does an action
     */
    @Override
    protected void onResume() {
        super.onResume();

        onClickDeleteButton();
        onClickEditButton();
        onClickCancelButton();
        onClickSaveButton(oldNameEditText, oldLimitEditText);
    }

    private void initComponents() {

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        viewModel.init(this);

        intent = getIntent();


        editTextName = findViewById(R.id.editCategoryName);
        editTextLimit = findViewById(R.id.editCategoryBudget);
        buttonSave = findViewById(R.id.editCategorySave);
        buttonCancel = findViewById(R.id.editCategoryCancel);
        imageButtonEdit = findViewById(R.id.editCategoryImageButton);
        buttonDelete = findViewById(R.id.editCategoryDelete);

        setDefaultNameForEditTexts();

        oldNameEditText = editTextName.getText().toString();
        oldLimitEditText = Integer.parseInt(editTextLimit.getText().toString());
    }


    // VIEW LOGIC METHODS
    private boolean deleteCategory() {

        final int id = intent.getIntExtra("categoryId", 0);
        final boolean isDeleted = viewModel.deleteCategory(id);

        return isDeleted;
    }

    private void updateSaveChanges(String oldName, int oldLimit){

        final String newName = editTextName.getText().toString();
        final int newLimit = Integer.parseInt(editTextLimit.getText().toString());

        final int id =  intent.getIntExtra("categoryId", 0);
        final int picture = intent.getIntExtra("categoryPicture",0);
        final String color = intent.getStringExtra("categoryColor");
        final String date = intent.getStringExtra("categoryDate");

        viewModel.editCategory(newName,id,newLimit,date,picture,color);

        if (!oldName.equals(newName) || newLimit != oldLimit) {
            Toast.makeText(getBaseContext(),"The category has been edited", Toast.LENGTH_LONG).show();
        }

    }

    private void enableEditCategoryButton() {

        final int categoryId = intent.getIntExtra("categoryId", 0);
        final boolean isDefault = viewModel.isDefaultCategory(categoryId);

        if(isDefault) {

            editTextName.setEnabled(false);
            editTextLimit.setEnabled(true);

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


    // ON CLICK METHODS

    private void onClickDeleteButton(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isDeleted = deleteCategory();

                if (isDeleted) {
                    Toast.makeText(getBaseContext(), "The category is deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "This category is default and can't be deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClickEditButton(){
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEditCategoryButton();
            }
        });
    }

    private void onClickCancelButton(){
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void onClickSaveButton(final String oldName, final int oldLimit){
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSaveChanges(oldName, oldLimit);
                finish();
            }
        });
    }


}
