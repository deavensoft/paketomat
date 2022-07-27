package com.deavensoft.paketomat.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorAttributes {
    private int code;
    private HttpStatus status;
    private String message;
}
