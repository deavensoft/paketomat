package com.deavensoft.paketomat.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PaketomatException extends Exception{
    private HttpStatus status;
    private int code;

    PaketomatException(String mess, HttpStatus s, int code){
        super(mess);
        this.status = s;
        this.code = code;
    }

}
