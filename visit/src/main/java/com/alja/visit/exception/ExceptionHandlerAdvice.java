package com.alja.visit.exception;

import com.alja.errors.ErrorResponseDto;
import com.alja.exception.PhysicianException;
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

    // TODO REFACTOR ->

    //fixme this response when request do admin svc
//    @ExceptionHandler(VisitException.class)
//    public ResponseEntity<VisitException> onVisitException(VisitException exception) {
//        logService.logError(exception.getMessage());
//        return new ResponseEntity<>(exception, exception.getHttpStatus());
//    }

    //fixme this response when request do visits svc DIRECTLY
    @ExceptionHandler(VisitException.class)
    public ResponseEntity<ErrorResponseDto> onPhysicianSpecializationNotFound(VisitException exception) {
        logService.logError(exception.getMessage());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage())
                .localDateTime(exception.getLocalDateTime())
                .httpStatus(exception.getHttpStatus())
                .build();
        return new ResponseEntity<>(errorResponseDto, exception.getHttpStatus());
    }

}
