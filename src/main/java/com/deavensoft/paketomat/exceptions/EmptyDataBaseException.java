package com.deavensoft.paketomat.exceptions;

public class EmptyDataBaseException extends Exception{
    private static final String MESS = "The database is empty";

    public EmptyDataBaseException(){
        super(MESS);
    }
}
