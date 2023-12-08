package com.alja.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiBusinessException extends RuntimeException{

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime localDateTime;
    private final String exceptionName;
    private final String errorName;

    public ApiBusinessException(String message, HttpStatus httpStatus, LocalDateTime localDateTime, String exceptionName, String errorName) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
        this.exceptionName = exceptionName;
        this.errorName = errorName;
    }

}
