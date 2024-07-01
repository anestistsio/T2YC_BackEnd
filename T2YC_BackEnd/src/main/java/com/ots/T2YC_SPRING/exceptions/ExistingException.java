package com.ots.T2YC_SPRING.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingException extends RuntimeException{

    public ExistingException(String message) {
        super(message);
    }
}
