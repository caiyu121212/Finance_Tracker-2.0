package com.github.caiyu121212.finance.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException{
    
    public BusinessException(String message){
        super(message);
    }

    public BusinessException(Srting message, Throwable cause){
        super(message,cause);
    }
}