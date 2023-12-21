package com.alja.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PatientDataRequestValidator.class)
@Documented
public @interface PatientData {
    String message() default "Invalid PatientDataFormat value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
