package com.example.laluna.ui.categories;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laluna.Model.Category;
import com.example.laluna.R;

import java.util.List;

/**
 * Adapter class that controls the listview in the view.
 * Data is taken by this class that will convert it into a list form
 * This class inherits from the existing class ArrayAdapter
 *
 * @author Ali Alkhaled
 * @author Deaa Khankan
 *
 */
public class CategoriesAdapter extends ArrayAdapter<Category> {


    public CategoriesAdapter(@NonNull Context context, List<Category> categoryList) {
        super(context, R.layout.custom_category, categoryList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater categoryInflater = LayoutInflater.from(getContext());

        View customCategory= categoryInflater.inflate(R.layout.custom_category,parent,false);

        Category singleCategoryItem = getItem(position);
        TextView  categoryName = (TextView) customCategory.findViewById(R.id.categoryName);
        TextView  categoryBudget = (TextView) customCategory.findViewById(R.id.categoryBudget);
        ImageView categoryImage = (ImageView) customCategory.findViewById(R.id.categoryImage);

        categoryName.setText(singleCategoryItem.get_name());
        categoryBudget.setText(singleCategoryItem.get_limit()+"");
        categoryImage.setImageResource(singleCategoryItem.get_pictureName());


        return customCategory;

    }
}
