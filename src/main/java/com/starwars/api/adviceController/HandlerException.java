package com.starwars.api.adviceController;


import com.starwars.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handledConstraintViolation(ConstraintViolationException exception, WebRequest webRequest)
    {
        List<String> errors = new ArrayList<>();
        for(ConstraintViolation constraintViolation: exception.getConstraintViolations())
        {
            errors.add(constraintViolation.getRootBeanClass().getName()+""+constraintViolation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,exception.getLocalizedMessage(),errors);
        return new ResponseEntity<Object>(apiError,new HttpHeaders(),apiError.getHttpStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
