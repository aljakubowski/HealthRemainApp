package com.alja.visit.exception;

import com.alja.exception.VisitException;
import com.alja.visit.service.LogService;
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

    @ExceptionHandler(VisitException.class)
    public ResponseEntity<VisitException> onVisitException(VisitException exception) {
        logService.logError(exception.getMessage());
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }

}
