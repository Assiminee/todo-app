package com.toDoList.controller;


import com.toDoList.exceptions.ResourceNotFoundException;
import com.toDoList.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ResourceNotFoundException ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<>("An unexpected error occurred: " +ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
