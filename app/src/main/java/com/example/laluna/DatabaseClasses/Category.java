package com.example.laluna.DatabaseClasses;

import java.util.Date;

public class Category {

    private int _id;
    private int _limit;
    private Date creationDate;
    private Date destroyedDate;
    private String _name;
    private String _pictureName;
    private String _color;

    public Category(int _limit, String _name, String _pictureName, String _color) {
        this._limit = _limit;
        this._name = _name;
        this._pictureName = _pictureName;
        this._color = _color;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public void set_color(String _color) {
        this._color = _color;
    }
}
