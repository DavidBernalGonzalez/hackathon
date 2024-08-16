package com.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.exceptions.DuplicateCityException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateCityException.class)
    public ResponseEntity<String> handleDuplicateCityException(DuplicateCityException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
