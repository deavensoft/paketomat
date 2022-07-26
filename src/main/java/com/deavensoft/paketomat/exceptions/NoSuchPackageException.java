package com.deavensoft.paketomat.exceptions;

public class NoSuchPackageException extends Exception {

    private static final String MESS = "There is no package with id ";

    public NoSuchPackageException(Long id){
        super(MESS + id);
    }
}
