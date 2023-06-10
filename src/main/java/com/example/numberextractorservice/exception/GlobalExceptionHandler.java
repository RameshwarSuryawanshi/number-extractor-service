package com.example.numberextractorservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IOException thrown by service.
     * @param ex exception
     * @return ResponseEntity
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while processing the file");
    }
}
