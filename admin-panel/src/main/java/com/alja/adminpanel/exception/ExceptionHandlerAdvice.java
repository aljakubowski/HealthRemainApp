package com.alja.adminpanel.exception;

import com.alja.adminpanel.service.LogService;
import com.alja.errors.ErrorResponseDto;
import com.alja.exception.PhysicianException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final LogService logService;

    @ExceptionHandler(PhysicianException.class)
    public ResponseEntity<ErrorResponseDto> onPhysicianSpecializationNotFound(PhysicianException exception) {
        logService.logError(exception.getMessage());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage())
                .localDateTime(exception.getLocalDateTime())
                .httpStatus(exception.getHttpStatus())
                .build();
        return new ResponseEntity<>(errorResponseDto, exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> onConstraintViolation(MethodArgumentNotValidException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .get();
        logService.logError(errorMessage);
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(errorMessage)
                .localDateTime(LocalDateTime.now())
                .httpStatus(httpStatus)
                .build();
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }
}
