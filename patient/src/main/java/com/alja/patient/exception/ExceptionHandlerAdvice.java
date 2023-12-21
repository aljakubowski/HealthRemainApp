package com.alja.patient.exception;

import com.alja.exception.PatientException;
import com.alja.patient.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final LogService logService;

    @ExceptionHandler(PatientException.class)
    public ResponseEntity<PatientException> onPhysicianSpecializationNotFound(PatientException exception) {
        logService.logError(exception.getMessage());
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }

}
