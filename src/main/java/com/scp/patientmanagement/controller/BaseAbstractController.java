package com.scp.patientmanagement.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.scp.patientmanagement.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseAbstractController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException (MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(fieldName, message);
        });

        return ResponseHandler.generateResponse("Bad request!", HttpStatus.BAD_REQUEST, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleValidationException (InvalidFormatException invalidFormatException) {
        Map<String, String> errors = new HashMap<>();

        invalidFormatException.getPath().forEach((error) -> {
            String fieldName = error.getFieldName();
            String message = "Invalid value!";

            errors.put(fieldName, message);
        });

        return ResponseHandler.generateResponse("Bad request!", HttpStatus.BAD_REQUEST, errors);
    }

}
