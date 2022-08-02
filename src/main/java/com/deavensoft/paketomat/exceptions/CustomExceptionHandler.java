package com.deavensoft.paketomat.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({PaketomatException.class})
    public ResponseEntity<Object> handleCustomException(PaketomatException e){
        ErrorAttributes error;
        error = new ErrorAttributes(e.getCode(), e.getStatus(), e.getMessage());
        if(e.getCode() == 200) log.info(e.getMessage());
        else log.error(e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Object> handleException(NumberFormatException e){
        ErrorAttributes error;
        error = new ErrorAttributes(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }
}
