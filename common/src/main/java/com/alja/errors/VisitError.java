package com.alja.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum VisitError {

    VISIT_COINCIDE_ERROR(Descriptions.VISIT_COINCIDE_ERROR, HttpStatus.BAD_REQUEST),
    VISIT_NOT_FOUND_ERROR(Descriptions.VISIT_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND),
    VISIT_DATE_INVALID_RANGE_ERROR(Descriptions.VISIT_DATE_INVALID_RANGE_ERROR, HttpStatus.NOT_FOUND),
    VISIT_PAST_DATE_INVALID_ERROR(Descriptions.VISIT_PAST_DATE_INVALID_ERROR, HttpStatus.NOT_FOUND),
    VISIT_FUTURE_DATE_INVALID_ERROR(Descriptions.VISIT_FUTURE_DATE_INVALID_ERROR, HttpStatus.NOT_FOUND),
    VISIT_INVALID_STATUS_ERROR(Descriptions.VISIT_INVALID_STATUS_ERROR, HttpStatus.NOT_FOUND),
    VISIT_INVALID_AVAILABLE_STATUS_ERROR(Descriptions.VISIT_INVALID_AVAILABLE_STATUS_ERROR, HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;

    VisitError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static class Descriptions {
        private static final String VISIT_COINCIDE_ERROR = "Visit coincide with physician already appointed visits";
        private static final String VISIT_NOT_FOUND_ERROR = "Visit does not exist";
        private static final String VISIT_DATE_INVALID_RANGE_ERROR = "Visit date 'from' cannot be after date 'to'";
        private static final String VISIT_PAST_DATE_INVALID_ERROR = "Available and Reserved Visits cannot take place in the past";
        private static final String VISIT_FUTURE_DATE_INVALID_ERROR = "Completed and Unrealized Visits cannot take place in the future";
        private static final String VISIT_INVALID_STATUS_ERROR = "Visit status invalid. Valid statuses: AVAILABLE, RESERVED, COMPLETED, UNREALIZED";
        private static final String VISIT_INVALID_AVAILABLE_STATUS_ERROR = "Visit status cannot be changed to Available when Patient is enrolled";
    }

}
