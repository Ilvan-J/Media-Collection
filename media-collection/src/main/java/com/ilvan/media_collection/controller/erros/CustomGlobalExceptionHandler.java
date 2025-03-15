package com.ilvan.media_collection.controller.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return buildErrorResponse("An internal server error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handlerValidationExceptions(MethodArgumentNotValidException ex) {
        var message = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            message.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });

        return buildErrorResponse(message.toString(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<CustomErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        var erroResponse = new CustomErrorResponse(status.value(),
                message,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy: HH:mm:ss")));
        return new ResponseEntity<>(erroResponse, status);
    }
}
