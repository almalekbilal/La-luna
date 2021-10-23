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
 * @author Deaa Khankan
 */
public class CategoriesEditActivity extends AppCompatActivity {

    private CategoriesViewModel viewModel;
    private EditText editTextName, editTextLimit;
    private Button buttonSave, buttonCancel, buttonDelete;
    private ImageButton imageButtonEdit;
    private Intent intent;


    /**
     * This method is responsible for showing all components i this activity and update them
     * It initializes all the data fields and the components in this class
     * It updates and activates all the components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        initComponents();

        setDefaultNameForEditTexts();

        final String oldName = editTextName.getText().toString();
        final int oldLimit = Integer.parseInt(editTextLimit.getText().toString());

        onClickDeleteButton();
        onClickEditButton();
        onClickCancelButton();
        onClickSaveButton(oldName, oldLimit);


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
    }


    // VIEW LOGIC METHODS
    private boolean deleteCategory() {

        int id = intent.getIntExtra("categoryId", 0);
        boolean isDeleted = viewModel.deleteCategory(id);

        return isDeleted;
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

    private void enableEditCategoryButton() {

        int categoryId = intent.getIntExtra("categoryId", 0);
        boolean isDefault = viewModel.isDefaultCategory(categoryId);

        if (isDefault) {

            if (editTextName.isEnabled() && editTextLimit.isEnabled()) {
                editTextName.setEnabled(false);
                editTextLimit.setEnabled(false);
            } else {
                editTextLimit.setEnabled(true);
            }
        }

        else {
            if (editTextName.isEnabled() && editTextLimit.isEnabled()) {
                editTextName.setEnabled(false);
                editTextLimit.setEnabled(false);
            } else {
                editTextName.setEnabled(true);
                editTextLimit.setEnabled(true);
            }
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