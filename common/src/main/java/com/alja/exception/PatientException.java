package com.alja.exception;


import com.alja.errors.PatientError;

import java.time.LocalDateTime;

public class PatientException extends ApiBusinessException {

    public PatientException(PatientError patientError) {
        super(patientError.getMessage(),
                patientError.getHttpStatus(),
                LocalDateTime.now(),
                PatientException.class.getSimpleName(),
                patientError.name());
    }
}
