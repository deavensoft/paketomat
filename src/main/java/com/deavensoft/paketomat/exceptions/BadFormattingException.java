package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class BadFormattingException extends PaketomatException{
    public BadFormattingException(String message, HttpStatus status, int code) {
        super(message, status, code);
    }
}
