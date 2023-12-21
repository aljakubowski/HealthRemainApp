package com.alja.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PatientError {

    PATIENT_NOT_FOUND_ERROR(Descriptions.PATIENT_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND),
    PATIENT_MINOR_ERROR(Descriptions.PATIENT_MINOR_ERROR, HttpStatus.FORBIDDEN),

    PATIENT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR(Descriptions.PATIENT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST),
    PATIENT_PHONE_NUMBER_ALREADY_EXISTS_ERROR(Descriptions.PATIENT_PHONE_NUMBER_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST),
    PATIENT_EMAIL_ALREADY_EXISTS_ERROR(Descriptions.PATIENT_EMAIL_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST),

    PATIENT_DATA_FORMAT_ERROR(Descriptions.PATIENT_DATA_FORMAT_ERROR, HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    PatientError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static class Descriptions {
        private static final String PATIENT_NOT_FOUND_ERROR = "Patient does not exist";
        private static final String PATIENT_MINOR_ERROR = "Patient is minor";
        private static final String PATIENT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR = "Patient social security number already exists";
        private static final String PATIENT_PHONE_NUMBER_ALREADY_EXISTS_ERROR = "phone number already exists";
        private static final String PATIENT_EMAIL_ALREADY_EXISTS_ERROR = "email already exists";
        private static final String PATIENT_DATA_FORMAT_ERROR = "Patient data format is wrong. Available formats: DETAILS, VISITS";
    }

}
