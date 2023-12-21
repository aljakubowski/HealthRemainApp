package com.alja.common.annotation;

import com.alja.common.enums.PatientDataFormat;
import com.alja.errors.PatientError;
import com.alja.exception.PatientException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PatientDataRequestValidator implements ConstraintValidator<PatientData, String> {

    @Override
    public boolean isValid(String patientDataFormat, ConstraintValidatorContext constraintValidatorContext) {
        if (patientDataFormat == null) {
            return true;
        }
        for (PatientDataFormat enumValue : PatientDataFormat.values()) {
            if (enumValue.name().equalsIgnoreCase(patientDataFormat)) {
                return true;
            }
        }
        throw new PatientException(PatientError.PATIENT_DATA_FORMAT_ERROR);
    }

}
