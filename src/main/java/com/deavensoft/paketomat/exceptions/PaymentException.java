package com.deavensoft.paketomat.exceptions;


import org.springframework.http.HttpStatus;

public class PaymentException extends PaketomatException {
    public PaymentException(String message, HttpStatus status, int code) {
        super(message, status, code);
    }
}
