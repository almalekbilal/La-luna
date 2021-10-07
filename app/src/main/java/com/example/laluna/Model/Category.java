package com.example.laluna.Model;

import java.util.Date;

/**
 * The category class holds information about a specific category in the database
 *
 *
 * @auther (Bilal Al Malek)
 * @auther (Deaa Khankan)
 * @auther (Ali Malla)
 * @auther (Ali Al Khaled)
 */
public class Category {

    private int _id;
    private int _limit;
    private Date creationDate;
    private Date destroyedDate;
    private String _name;
    private String _pictureName;
    private String _color;

    public Category(int _id, int _limit, String _name, String _pictureName, String _color, Date creationDate, Date destroyedDate) {
        this._id = _id;
        this._limit = _limit;
        this._name = _name;
        this._pictureName = _pictureName;
        this._color = _color;
        this.creationDate = creationDate;
        this.destroyedDate = destroyedDate;
    }

    public int get_id() {
        return _id;
    }

    public Date getCreationDate() {
        return creationDate;
    }


    public Date getDestroyedDate() {
        return destroyedDate;
    }

    public void setDestroyedDate(Date destroyedDate) {
        this.destroyedDate = destroyedDate;
    }

    public int get_limit() {
        return _limit;
    }

    public void set_limit(int _limit) {
        this._limit = _limit;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_pictureName() {
        return _pictureName;
    }

    public void set_pictureName(String _pictureName) {
        this._pictureName = _pictureName;
    }

    public String get_color() {
        return _color;
    }

    /**
     * The method compairs the id of two categories and return true if they match
     * @param o the other category
     * @return true if ids equals
     */
    @Override
    public boolean equals(Object o){
        Category c2 = (Category) o;

        return this._id == c2.get_id();
    }

    /*public void set_color(String _color) {
        this._color = _color;
    }*/
}
