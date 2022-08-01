package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class CityException extends PaketomatException{

    public CityException(String message, HttpStatus status, int code) {
        super(message, status, code);
    }
}
