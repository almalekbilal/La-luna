package com.example.laluna.ui.home.addUpdateExpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.laluna.R;

public class AddExpenseActivity extends AppCompatActivity {

    AddExpenseViewModel addExpenseViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_expense);

        addExpenseViewModel =
                ViewModelProviders.of(this).get(AddExpenseViewModel.class);

        addExpenseViewModel.init(this);

        final Spinner spn = findViewById(R.id.spn_category);
        final Context context = this;

        addExpenseViewModel.getcategoriesNames().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                ArrayAdapter<CharSequence> ad  = new ArrayAdapter(context, android.R.layout.simple_spinner_item, strings);

                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spn.setAdapter(ad);
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addExpenseViewModel.setSelectedCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button okButton = findViewById(R.id.btn_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String expenseName = ((EditText) findViewById(R.id.edt_expenseName)).getText().toString();
                    int expenseValue = Integer.parseInt(((EditText) findViewById(R.id.edt_expenseValue)).getText().toString());
                    addExpenseViewModel.addExp(expenseName, expenseValue);
                }catch (Exception e){

                }


                finish();
            }
        });
    }
}