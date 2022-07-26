package com.deavensoft.paketomat.exceptions;

public class NoSuchCourierException extends Exception{
    private static final String MESS = "There is no courier with id ";

    public NoSuchCourierException(Long id){
        super(MESS + id);
    }
}
