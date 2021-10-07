package com.example.laluna.View.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laluna.Model.Expense;
import com.example.laluna.R;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpenseHolder> {

    List<Expense> expenses;
    Context context;
    public ExpensesAdapter(Context context, List<Expense> expenses){
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpensesAdapter.ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_expense_view, parent, false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesAdapter.ExpenseHolder holder, int position) {
        holder.price.setText(expenses.get(position).get_value() + " Kr");
        holder.name.setText(expenses.get(position).get_name());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ExpenseHolder extends RecyclerView.ViewHolder {

        TextView price, name;
        ImageView categoryImage;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.txv_expensePrice);
            name = itemView.findViewById(R.id.txv_expenseName);
            categoryImage = itemView.findViewById(R.id.img_categoryImage);

        }
    }
}
