package com.example.laluna.ui.analys;

import com.example.laluna.Model.Category;

/**
 * The class contains a category and its budget and total money spent in a month
 * It makes the communication between the view model and the view easier
 *
 * @author (Bilal Al Malek)
 * @author (Ali Malla)
 */
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
