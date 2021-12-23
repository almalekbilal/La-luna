package com.example.laluna.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * The category class holds information about a specific expense in the database
 *
 *
 * @auther (Bilal Al Malek)
 * @auther (Deaa Khankan)
 * @auther (Ali Malla)
 * @auther (Ali Alkhaled)
 */
public class Expense extends BudgetItem implements Serializable{
    private int _value;
    private Category _category;

    public Expense(int _id ,String _name, int _value , Date _date, Category _category) {
        super(_id, _name, _date);
        this._value = _value;
        this._category = _category;
    }


    public int get_value() {
        return _value;
    }

    public void set_value(int _price) {
        this._value = _price;
    }

    public Category get_category() {
        return _category;
    }

    public void set_category(Category _category) {
        this._category = _category;
    }

    /**
     * The method compairs the id of two expenses and return true if they match
     * @param o the other category
     * @return true if ids equals
     */
    @Override
    public boolean equals(Object o){
        return this.get_id() == ((Expense)o).get_id();
    }




















    public void update(String _name, int _value , Date _date, Category _category) {
        set_name(_name);
        set_value(_value);
        set_creationDate(_date);
        set_category(_category);
    }

}
