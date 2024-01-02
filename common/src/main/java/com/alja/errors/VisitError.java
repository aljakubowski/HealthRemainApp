package com.alja.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum VisitError {
//fixme refactor
    VISIT_COINCIDE_ERROR(Descriptions.VISIT_COINCIDE_ERROR, HttpStatus.BAD_REQUEST);
//    VISIT_NOT_FOUND_ERROR(Descriptions.VISIT_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
//    VISIT_NOT_FOUND_ERROR(Descriptions.VISIT_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND),

//    VISIT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR(Descriptions.VISIT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST),

//    VISIT_DATA_FORMAT_ERROR(Descriptions.VISIT_DATA_FORMAT_ERROR, HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    VisitError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static class Descriptions {
        private static final String VISIT_COINCIDE_ERROR = "Visit coincide with physician already appointed visits";
//        private static final String VISIT_NOT_FOUND_ERROR = "Visit does not exist";
//        private static final String VISIT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR = "Visit social security number already exists";
//        private static final String VISIT_DATA_FORMAT_ERROR = "Visit data format is wrong. Available formats: DETAILS, VISITS";
    }

}
