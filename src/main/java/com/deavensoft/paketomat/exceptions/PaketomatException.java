package com.deavensoft.paketomat.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@RequiredArgsConstructor
public class PaketomatException extends Exception{
    private final String message;
    private final HttpStatus status;
    private final int code;
}