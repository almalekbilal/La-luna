package com.example.laluna.Model.exceptions;

public class CategoryIrrelevantLimitException extends Exception{
    public CategoryIrrelevantLimitException(){
        super("limit cannot be negative");
    }
}
