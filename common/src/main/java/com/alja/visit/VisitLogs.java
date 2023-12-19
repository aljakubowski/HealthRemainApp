package com.alja.visit;

import lombok.Getter;

@Getter
public enum VisitLogs {

    ADD_VISIT("adding new Physician specialization: {}"),

//    GET_VISIT("getting list of all Specializations"),
//    UPDATE_SPECIALIZATION("updating physician Specialization name from {} to {}"),
    DELETE_VISIT("deleting specialization: {}");


    public final String logMessage;

    VisitLogs(String logMessage) {
        this.logMessage = logMessage;
    }
}
