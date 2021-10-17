package com.example.laluna.ui.analys.categoryExpensesActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laluna.Model.Expense;
import com.example.laluna.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter {


    private List<Expense> expenses;

    private int id;


    Context context;
    LayoutInflater layoutInflater;
    public ListViewAdapter(List<Expense> expenses, Context context, int id) {
        super(context, R.layout.custom_expense_view,expenses);
        this.expenses= expenses;
        this.context = context;
        this.id = id;


    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(layoutInflater == null) {
            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_expense_view,null);
        }


        TextView txv_expensePrice = convertView.findViewById(R.id.txv_expensePrice);
        TextView txv_expenseName = convertView.findViewById(R.id.txv_expenseName);
        ImageView img_categoryImage = convertView.findViewById(R.id.img_categoryImage);
        TextView txv_dateExpense = convertView.findViewById(R.id.txv_dateExpense);


        if(expenses.get(position).get_category().get_id() == id){
            txv_expensePrice.setText(Integer.toString(expenses.get(position).get_value()));
            txv_expenseName.setText(expenses.get(position).get_name());
            img_categoryImage.setImageResource(expenses.get(position).get_category().get_pictureName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            txv_dateExpense.setText(sdf.format(expenses.get(position).get_date()));
        }




        return convertView;
    }
}
