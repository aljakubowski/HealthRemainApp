package com.alja.exception;


import com.alja.errors.PhysicianError;

import java.time.LocalDateTime;

public class PhysicianException extends ApiBusinessException {

    public PhysicianException(PhysicianError physicianError) {
        super(physicianError.getMessage(),
                physicianError.getHttpStatus(),
                LocalDateTime.now(),
                PhysicianException.class.getSimpleName(),
                physicianError.name());
    }
}
