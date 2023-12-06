package com.alja.physician.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class LogService {
    public void logOperation(String operation){
        log.info(operation);
    }
    public void logOperation(String operation, String arg1){
        log.info(operation, arg1);
    }

    public void logOperation(String operation, String arg1, String arg2){
        log.info(operation, arg1, arg2);
    }
}
