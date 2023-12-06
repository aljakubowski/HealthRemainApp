package com.alja.exception;


import com.alja.errors.PhysicianError;

import java.time.LocalDateTime;

public class PhysicianException extends ApiException {

    public PhysicianException(PhysicianError physicianError) {
        super(physicianError.getMessage(), physicianError.getHttpStatus(), LocalDateTime.now());
    }
}
