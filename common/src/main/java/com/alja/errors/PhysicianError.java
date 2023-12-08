package com.alja.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PhysicianError {

    PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR(Descriptions.PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND),
    PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR(Descriptions.PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    PhysicianError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static class Descriptions {
        private static final String PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR = "Physician Specialization does not exist";
        private static final String PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR = "Physician Specialization already exists";     //todo pass parameter
    }

}


