package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends PaketomatException{
    public NoSuchUserException(String mess, HttpStatus s, int code){
        super(mess, s, code);
    }
}
