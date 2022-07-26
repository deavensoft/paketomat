package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchPackageException extends PaketomatException {
    public NoSuchPackageException(String mess, HttpStatus s, int code){
        super(mess, s, code);
    }
}
