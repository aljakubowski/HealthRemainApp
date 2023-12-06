package com.alja.physician;

import lombok.Getter;

@Getter
public enum PhysicianLogs {

    PHYSICIAN("Physician: "),
    GET_SPECIALIZATIONS(PHYSICIAN.logMessage + "getting list of all Specializations"),
    ADD_SPECIALIZATION(PHYSICIAN.logMessage + "adding new Physician specialization: {}"),
    UPDATE_SPECIALIZATION(PHYSICIAN.logMessage +"updating physician Specialization name from {} to {}"),
    DELETE_SPECIALIZATION(PHYSICIAN.logMessage +"deleting specialization: {}");

    public final String logMessage;

    PhysicianLogs(String logMessage) {
        this.logMessage = logMessage;
    }

}
