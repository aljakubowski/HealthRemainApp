package com.alja.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(PhysicianException.class)
    public ResponseEntity<Object> onPhysicianSpecializationNotFound(PhysicianException exception) {
        ApiException apiException = new ApiException(exception.getMessage(),
                exception.getHttpStatus(),
                exception.getLocalDateTime());
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    @ExceptionHandler(PhysicianException.class)
    public ResponseEntity<Object> onPhysician(PhysicianException exception) {
        ApiException apiException = new ApiException(exception.getMessage(),
                exception.getHttpStatus(),
                exception.getLocalDateTime());
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

}
