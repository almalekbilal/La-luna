package com.example.laluna.Model.exceptions;

public class IrrelevantDateException extends Exception{

    public IrrelevantDateException(){
        super("End date cannot be before start date");
    }
}
