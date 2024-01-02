package com.alja.visit;

import lombok.Getter;

@Getter
public enum VisitLogs {

    ADD_VISIT("adding new Visit of id: {}"),
    GET_ALL_VISITS("getting list of all Visits"),

//    GET_VISIT("getting list of all Specializations"),
//    UPDATE_SPECIALIZATION("updating physician Specialization name from {} to {}"),
    DELETE_VISIT("deleting specialization: {}");


    public final String logMessage;

    VisitLogs(String logMessage) {
        this.logMessage = logMessage;
    }
}
