package com.alja.patient;

import lombok.Getter;

@Getter
public enum PatientLogs {

    REGISTER_NEW_PATIENT("adding new Patient: {} {}"),
    GET_ALL_PATIENTS("getting list of all Patients"),
    GET_PATIENT_BY_ID("getting Patient with id: {}"),
    UPDATE_PATIENT("updating Patient with id: {}"),
    DELETE_PATIENT_BY_ID("deleting Patient with id: {}");

    public final String logMessage;

    PatientLogs(String logMessage) {
        this.logMessage = logMessage;
    }

}
