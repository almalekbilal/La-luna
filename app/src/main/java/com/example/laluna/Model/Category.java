package com.example.laluna.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The category class holds information about a specific category in the database
 *
 *
 * @auther (Bilal Al Malek)
 * @auther (Deaa Khankan)
 * @auther (Ali Malla)
 * @auther (Ali Al Khaled)
 */
public class Category extends BudgetItem implements Serializable {

    private int _id;
    private int _limit;
    private Date creationDate;
    private Date destroyedDate;
    private String _name;
    private int _pictureName;
    private String _color;



    public Category(int _id, int _limit, String _name, int _pictureName, String _color, Date creationDate, Date destroyedDate) {
        super(_id, _name, creationDate);
        this._id = _id;
        this._limit = _limit;
        this._name = _name;
        this._pictureName = _pictureName;
        this._color = _color;
        this.creationDate = creationDate;
        this.destroyedDate = destroyedDate;

    }



    public Date get_creationDate() {
        return get_date();
    }
    public Date getDestroyedDate() { return destroyedDate; }

    public void setDestroyedDate(Date destroyedDate) {
        this.destroyedDate = destroyedDate;
    }

    public int get_limit() {
        return _limit;
    }

    public void set_limit(int _limit) {
        this._limit = _limit;
    }

    public int get_pictureName() {
        return _pictureName;
    }

    public void set_pictureName(int _pictureName) {
        this._pictureName = _pictureName;
    }

    public String get_color() {
        return _color;
    }

    /**
     * The method compares the id of two categories and return true if they match
     * @param o the other category
     * @return true if ids equals
     */
    @Override
    public boolean equals(Object o){
        Category c2 = (Category) o;

        return this._id == c2.get_id();
    }
}
