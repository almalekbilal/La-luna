package com.example.laluna.Model.exceptions;

public class NoLimitExistingException extends Exception{
    public NoLimitExistingException(){
        super("There is no limit found in for the category in this month");
    }
}