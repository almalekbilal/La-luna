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
import android.widget.Toast;

import com.example.laluna.Model.Expense;
import com.example.laluna.R;


/**
 * A class that is responsible for the view of the activity where the user can update an expense
 *
 * @author Bilal Al Malek
 * @author Ali Malla
 */
public class UpdateExpenseActivity extends AppCompatActivity {

    UpdateExpenseViewModel updateExpenseViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_expense);

        updateExpenseViewModel = ViewModelProviders.of(this).get(UpdateExpenseViewModel.class);


        Bundle data = getIntent().getExtras();
        Expense e = (Expense) data.getSerializable("expense");

        updateExpenseViewModel.init(this, e);


        EditText ExpenseName = findViewById(R.id.edt_expenseName);
        EditText ExpenseValue = findViewById(R.id.edt_expenseValue);
        Button deleteButton = findViewById(R.id.btn_delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateExpenseViewModel.deleteExpense();
                Toast.makeText(getBaseContext(), "Expense has been deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        });



        ExpenseName.setText(e.get_name());
        ExpenseValue.setText(String.valueOf(e.get_value()));

        final Spinner spn = findViewById(R.id.spn_category);
        final Context context = this;

        updateExpenseViewModel.getcategoriesNames().observe(this, new Observer<String[]>() {
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
                updateExpenseViewModel.setSelectedCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button ok = findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String expenseName = ((EditText) findViewById(R.id.edt_expenseName)).getText().toString();
                    int expenseValue = Integer.parseInt(((EditText) findViewById(R.id.edt_expenseValue)).getText().toString());
                    updateExpenseViewModel.updateExp(expenseName, expenseValue);
                }catch (Exception e){

                }
                finish();
            }
        });

    }
}