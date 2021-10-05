package com.example.laluna.View.ui.analys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laluna.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {




   // private final List<CategoryWithMoney> categoryWithMoneyList;

    private final String [] name;
    private int [] images;
    Context context;
    LayoutInflater layoutInflater;

    public GridViewAdapter( String[] name, int[] images,Context context) {
        this.name = name;
        this.images = images;
        this.context = context;
       // this.categoryWithMoneyList = categoryWithMoneyList;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(layoutInflater == null) {
            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null){
            view = layoutInflater.inflate(R.layout.customcategoryanalysis_analysis,null);
        }


        ImageView categoryAnalysis = (ImageView) view.findViewById(R.id.categoryAnalysis);
        TextView categorySpentMoney = (TextView) view.findViewById(R.id.categorySpentMoney);

      //  categorySpentMoney.setText(categoryWithMoneyList.get(i).spent);
        categorySpentMoney.setText(name[i]);
        categoryAnalysis.setImageResource(images[i]);

        return view;
    }
}
