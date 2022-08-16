package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchStatusException extends PaketomatException{
    public NoSuchStatusException(String message, HttpStatus status, int code) {
        super(message, status, code);
    }
}
