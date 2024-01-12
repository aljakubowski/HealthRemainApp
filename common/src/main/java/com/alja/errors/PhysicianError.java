package com.alja.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PhysicianError {

    PHYSICIAN_NOT_FOUND_ERROR(Descriptions.PHYSICIAN_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND),

    PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR(Descriptions.PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND),
    PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR(Descriptions.PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST),
    PHYSICIAN_PHONE_NUMBER_ALREADY_EXISTS_ERROR(Descriptions.PHYSICIAN_PHONE_NUMBER_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST),
    PHYSICIAN_EMAIL_ALREADY_EXISTS_ERROR(Descriptions.PHYSICIAN_EMAIL_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST),
    PHYSICIAN_APPOINTED_VISITS_ERROR(Descriptions.PHYSICIAN_APPOINTED_VISITS_ERROR, HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;

    PhysicianError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static class Descriptions {
        private static final String PHYSICIAN_NOT_FOUND_ERROR = "Physician does not exist";
        private static final String PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR = "Physician Specialization does not exist";
        private static final String PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR = "Physician Specialization already exists";
        private static final String PHYSICIAN_PHONE_NUMBER_ALREADY_EXISTS_ERROR = "phone number already exists";
        private static final String PHYSICIAN_EMAIL_ALREADY_EXISTS_ERROR = "email already exists";
        private static final String PHYSICIAN_APPOINTED_VISITS_ERROR = "Physician could not be updated because of appointed visits";
    }

}
