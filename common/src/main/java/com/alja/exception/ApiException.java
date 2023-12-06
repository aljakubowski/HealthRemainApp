package com.alja.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiException extends RuntimeException{

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime localDateTime;

    public ApiException(String message, HttpStatus httpStatus, LocalDateTime localDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }

}
