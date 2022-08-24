package com.example.laluna.Model.exceptions;

public class ExpenseIrrelevantNameException extends Exception{
    public ExpenseIrrelevantNameException(){
        super("Name of expense can not be empty");
    }
}
