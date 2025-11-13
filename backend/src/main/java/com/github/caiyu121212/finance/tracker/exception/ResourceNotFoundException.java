package com.github.caiyu121212.finance.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResoureNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue){
        super(String.format("%s not found with %s : '%s'",resourceName, fieldName,fieldValue));
    }

}
