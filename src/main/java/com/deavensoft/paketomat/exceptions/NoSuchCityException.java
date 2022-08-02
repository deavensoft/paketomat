package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchCityException extends PaketomatException{

    public NoSuchCityException(String mess, HttpStatus s, int code){
        super(mess, s, code);
    }

}
