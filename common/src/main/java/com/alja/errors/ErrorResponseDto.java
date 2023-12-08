package com.alja.errors;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponseDto {
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime localDateTime;
}
