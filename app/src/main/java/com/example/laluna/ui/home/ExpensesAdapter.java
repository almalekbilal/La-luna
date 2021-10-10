package com.example.laluna.ui.home;

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

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The adapter that controls the recyclerview in the view
 *Its take the data and place them in the recyclerview in a list form
 *
 *   @auther (Deaa Khankan)
 *   @auther (Ali Al Khaled)
 */
public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpenseHolder> {

    List<Expense> expenses;
    Context context;
    public ExpensesAdapter(Context context, List<Expense> expenses){
        this.expenses = expenses;
        this.context = context;
    }

    /**
     * The methods is responsible for creating the view for an item
     * @param parent
     * @param viewType
     * @return ExpenseHolder object
     */
    @NonNull
    @Override
    public ExpensesAdapter.ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_expense_view, parent, false);
        return new ExpenseHolder(view);
    }

    /**
     * This is responsible for taking the data from the list and putting them into the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ExpensesAdapter.ExpenseHolder holder, int position) {
        holder.price.setText(expenses.get(position).get_value() + " Kr");
        holder.name.setText(expenses.get(position).get_name());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        holder.date.setText(sdf.format(expenses.get(position).get_date()));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    /**
     * The class holds the information about the view object in the custom layout
     */
    public class ExpenseHolder extends RecyclerView.ViewHolder {

        TextView price, name, date;
        ImageView categoryImage;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.txv_expensePrice);
            name = itemView.findViewById(R.id.txv_expenseName);
            categoryImage = itemView.findViewById(R.id.img_categoryImage);
            date = itemView.findViewById(R.id.txv_dateExpense);


        }
    }
}
