package com.alja.visit;

import lombok.Getter;

@Getter
public enum VisitLogs {
    ADD_VISIT("adding new Visit of id: {}"),
    GET_VISIT("getting Visit with id: {}"),
    GET_ALL_VISITS("getting list of all Visits"),
    GET_VISITS_FILTERED("getting list of filtered Visits"),
    UPDATE_VISIT("updating Visit with id: {}"),
    DELETE_VISIT("deleting Visit with id: {}"),
    GET_ALL_PATIENT_VISITS("getting list of all Visits of Patient of id: {}"),
    APPOINTING_VISIT("Patient of id {} appointing visit of id: {}"),
    CANCELING_VISIT("Patient of id {} canceling visit of id: {}");

    public final String logMessage;

    VisitLogs(String logMessage) {
        this.logMessage = logMessage;
    }
}
