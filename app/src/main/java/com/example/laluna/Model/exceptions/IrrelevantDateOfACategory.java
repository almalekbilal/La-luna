package com.example.laluna.Model.exceptions;

public class IrrelevantDateOfACategory extends Exception{
    public IrrelevantDateOfACategory(){
        super("Irrelevant date for the category");
    }
    public IrrelevantDateOfACategory(String message){
        super(message);
    }
}
