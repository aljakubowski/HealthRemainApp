package com.alja.physician.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class LogService {
    private final String OPERATION = "Operation: ";
    private final String ERROR = "Error: ";

    public void logOperation(String operation) {
        log.info(OPERATION + operation);
    }

    public void logOperation(String operation, String arg1) {
        log.info(OPERATION + operation, arg1);
    }

    public void logOperation(String operation, String arg1, String arg2) {
        log.info(OPERATION + operation, arg1, arg2);
    }

    public void logOperation(String operation, String arg1, String arg2, String arg3) {
        log.info(OPERATION + operation, arg1, arg2);
    }

    public void logError(String message) {
        log.error(ERROR + message);
    }
}
