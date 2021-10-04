package com.example.laluna.View.ui.analys;

import com.example.laluna.Model.Category;

public class CategoryWithMoney {

    Category category;
    int limit;
    int spent;

    public CategoryWithMoney(Category category, int limit, int spent) {
        this.category = category;
        this.limit = limit;
        this.spent = spent;
    }
}
