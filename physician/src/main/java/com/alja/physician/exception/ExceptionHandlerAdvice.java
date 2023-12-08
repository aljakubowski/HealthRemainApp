package com.alja.physician.exception;

import com.alja.exception.PhysicianException;
import com.alja.physician.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final LogService logService;

    @ExceptionHandler(PhysicianException.class)
    public ResponseEntity<PhysicianException> onPhysicianSpecializationNotFound(PhysicianException exception) {
        ////       todo  log error response?cep
        logService.logError(exception.getMessage());
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

}
