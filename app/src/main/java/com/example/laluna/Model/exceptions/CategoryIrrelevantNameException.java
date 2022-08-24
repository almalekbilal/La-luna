package com.example.laluna.Model.exceptions;

public class CategoryIrrelevantNameException extends Exception{
    public CategoryIrrelevantNameException(){
        super("Category name cannot be empty");
    }
}
