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

import java.sql.Date;


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



        final String oldName=editCategoryName.getText().toString();
        final int oldBudget=Integer.parseInt(editCategoryBudget.getText().toString());




            editCategoryImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editCategoryName.setEnabled(true);
                    editCategoryBudget.setEnabled(true);

                }
            });

            editCategoryCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }



            );


            editCategorySave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String newName = editCategoryName.getText().toString();
                    int newLimit = Integer.parseInt(editCategoryBudget.getText().toString());
                    int id =  intent.getIntExtra("categoryId", 0);
                    String color = intent.getStringExtra("categoryColor");
                    String date = intent.getStringExtra("categoryDate");
                    int picture = intent.getIntExtra("categoryPicture",0);



                    viewModel.editCategory(newName,id,newLimit,date,picture,color);
                    if (! oldName.equals(newName) || newLimit !=oldBudget) {
                        Toast.makeText(getBaseContext(),"The category has been edited", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }


            });


    }
}