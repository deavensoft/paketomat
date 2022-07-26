package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchCourierException extends PaketomatException{
    public NoSuchCourierException(String mess, HttpStatus s, int code){
        super(mess, s, code);
    }
}
