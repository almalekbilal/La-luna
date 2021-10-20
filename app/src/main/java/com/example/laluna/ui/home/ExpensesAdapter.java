package com.example.laluna.ui.home;

import android.content.Context;
import android.util.Log;
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
    recycleListener listener;
    public ExpensesAdapter(Context context, List<Expense> expenses, recycleListener listener){
        this.expenses = expenses;
        this.context = context;
        this.listener = listener;
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
        return new ExpenseHolder(view, listener);
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
        holder.categoryImage.setImageResource(expenses.get(position).get_category().get_pictureName());
        listener.onScroll(position);

        Log.i("Roro",expenses.size()+"");
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    /**
     * The class holds the information about the view object in the custom layout
     */
    public class ExpenseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView price, name, date;
        ImageView categoryImage;
        recycleListener listener;

        public ExpenseHolder(@NonNull View itemView, recycleListener listener) {
            super(itemView);

            price = itemView.findViewById(R.id.txv_expensePrice);
            name = itemView.findViewById(R.id.txv_expenseName);
            categoryImage = itemView.findViewById(R.id.img_categoryImage);
            date = itemView.findViewById(R.id.txv_dateExpense);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("Listener", "Jag körs");
            listener.onExpenseClick(expenses.get(getAdapterPosition()));
        }
    }

    public interface recycleListener {
        void onExpenseClick(Expense expense);
        void onScroll(int position);
    }


}
