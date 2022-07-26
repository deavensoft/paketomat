package com.deavensoft.paketomat.exceptions;

public class NoSuchDispatcherException extends Exception{

    private static final String MESS = "There is no dispatcher with id ";

    public NoSuchDispatcherException(Long id){
        super(MESS + id);
    }
}
