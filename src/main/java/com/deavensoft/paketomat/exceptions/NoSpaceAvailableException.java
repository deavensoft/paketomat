package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class NoSpaceAvailableException extends PaketomatException{
    public NoSpaceAvailableException(String message, HttpStatus status, int code) {
        super(message, status, code);
    }
}
