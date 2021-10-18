package com.example.laluna.ui.addudateexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.laluna.Model.Expense;
import com.example.laluna.R;

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



    }
}