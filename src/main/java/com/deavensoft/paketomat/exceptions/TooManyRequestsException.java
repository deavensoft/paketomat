package com.deavensoft.paketomat.exceptions;

import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends PaketomatException {
    public TooManyRequestsException(String message, HttpStatus status, int code) {
        super(message, status, code);
    }
}
