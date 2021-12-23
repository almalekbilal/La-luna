package com.example.laluna.Model;

import java.util.Date;

public abstract class BudgetItem {
    private int _id;
    private String _name;
    private Date _creationDate;

    public BudgetItem(int _id, String _name, Date _creationDate) {
        this._id = _id;
        this._name = _name;
        this._creationDate = _creationDate;
    }

    public int get_id() {
        return _id;
    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Date get_date() {
        return _creationDate;
    }

    public void set_creationDate(Date _creationDate) {
        this._creationDate = _creationDate;
    }
}
