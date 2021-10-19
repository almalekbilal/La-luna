package com.example.laluna.ui.analysis.categoryExpensesActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laluna.Model.Expense;
import com.example.laluna.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter {


    private List<Expense> expenses;

    private int categoryId;

    private Context context;
    private LayoutInflater layoutInflater;



    private TextView txv_expensePrice;
    private TextView txv_expenseName;
    private ImageView img_categoryImage;
    private TextView txv_dateExpense;


    public ListViewAdapter(List<Expense> expenses, Context context, int categoryId) {
        super(context, R.layout.custom_expense_view,expenses);
        this.expenses= expenses;
        this.context = context;
        this.categoryId = categoryId;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(layoutInflater == null) {
            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_expense_view,null);
        }

        initExpenseInformation(convertView);

        showExpenseInformation(position);

        return convertView;
    }


    private void initExpenseInformation(View convertView){
        txv_expensePrice = convertView.findViewById(R.id.txv_expensePrice);
        txv_expenseName = convertView.findViewById(R.id.txv_expenseName);
        img_categoryImage = convertView.findViewById(R.id.img_categoryImage);
        txv_dateExpense = convertView.findViewById(R.id.txv_dateExpense);
    }


    private void showExpenseInformation(int position){
        if(checkCategoryId(position)){
            txv_expensePrice.setText(Integer.toString(expenses.get(position).get_value()));
            txv_expenseName.setText(expenses.get(position).get_name());
            img_categoryImage.setImageResource(expenses.get(position).get_category().get_pictureName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            txv_dateExpense.setText(sdf.format(expenses.get(position).get_date()));
        }
    }

    private boolean checkCategoryId(int position){
        return expenses.get(position).get_category().get_id() == categoryId;
    }
}
