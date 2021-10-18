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
 * @auther (Ali Al Khaled)
 */
public class Expense implements Serializable {
    private int _id;
    private String _name;
    private int _value;
    private Date _date;
    private Category _category;

    public Expense(int _id ,String _name, int _value , Date _date, Category _category) {
        this._id = _id;
        this._name = _name;
        this._value = _value;
        this._date = _date;
        this._category = _category;
    }

    public int get_id() {
        return _id;
    }

    /*public void set_id(int _id) {
        this._id = _id;
    }*/

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_value() {
        return _value;
    }

    public void set_value(int _price) {
        this._value = _price;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
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
}
