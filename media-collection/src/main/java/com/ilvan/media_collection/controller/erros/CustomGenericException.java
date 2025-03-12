package com.ilvan.media_collection.controller.erros;

import org.springframework.http.HttpStatus;

public class CustomGenericException extends RuntimeException{
    private final HttpStatus status;

    public CustomGenericException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
