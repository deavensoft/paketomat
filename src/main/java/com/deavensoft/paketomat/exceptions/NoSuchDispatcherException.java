package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchDispatcherException extends PaketomatException{
    public NoSuchDispatcherException(String mess, HttpStatus s, int code){
        super(mess, s, code);
    }
}
