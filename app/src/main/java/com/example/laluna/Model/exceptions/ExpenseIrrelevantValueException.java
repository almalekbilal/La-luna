package com.example.laluna.Model.exceptions;

public class ExpenseIrrelevantValueException extends Exception{

    public ExpenseIrrelevantValueException(){
        super("The value is irrelevant");
    }
}
