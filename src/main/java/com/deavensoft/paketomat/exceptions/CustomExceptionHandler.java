package com.deavensoft.paketomat.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;

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

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e){
        ErrorAttributes error;
        error = new ErrorAttributes(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({UnsupportedEncodingException.class})
    public ResponseEntity<Object> handleEncodeException(UnsupportedEncodingException e){
        ErrorAttributes error;
        error = new ErrorAttributes(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleEncodeException(NullPointerException e){
        ErrorAttributes error;
        error = new ErrorAttributes(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({PropertyValueException.class})
    public ResponseEntity<Object> handleDataException(PropertyValueException e){
        ErrorAttributes error;
        error = new ErrorAttributes(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }
}
