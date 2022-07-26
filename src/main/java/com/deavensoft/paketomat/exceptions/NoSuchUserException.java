package com.deavensoft.paketomat.exceptions;

public class NoSuchUserException extends Exception{

    private static final String MESS = "There is no user with id ";

    public NoSuchUserException(Long id){
        super(MESS + id);
    }
}
