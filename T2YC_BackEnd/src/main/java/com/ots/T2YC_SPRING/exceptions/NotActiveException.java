package com.ots.T2YC_SPRING.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotActiveException extends RuntimeException{

    public NotActiveException(String message) {
        super(message);
    }
}

