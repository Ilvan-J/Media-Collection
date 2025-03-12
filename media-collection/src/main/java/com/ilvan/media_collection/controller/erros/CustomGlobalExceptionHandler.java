package com.ilvan.media_collection.controller.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(CustomGenericException.class)
    public ResponseEntity<CustomErrorResponse> handlerGenericException(CustomGenericException ex) {
        return buildErrorResponse(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<CustomErrorResponse> handlerAllExceptions(Exception ex) {
        return buildErrorResponse("An internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<CustomErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        var erroResponse = new CustomErrorResponse(status.value(),
                message,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy: HH:mm:ss")));
        return new ResponseEntity<>(erroResponse, status);
    }
}
