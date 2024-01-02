package com.alja.exception;

import com.alja.errors.VisitError;
import java.time.LocalDateTime;

public class VisitException extends ApiBusinessException {

    public VisitException(VisitError visitError) {
        super(visitError.getMessage(),
                visitError.getHttpStatus(),
                LocalDateTime.now(),
                VisitException.class.getSimpleName(),
                visitError.name());
    }
}
