package com.deavensoft.paketomat.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({NoSuchPackageException.class, NoSuchDispatcherException.class,
    NoSuchUserException.class, NoSuchCourierException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e){
        log.error(e.getMessage());
        return e.getClass().getSimpleName() + ": "+ e.getMessage();
    }
}
