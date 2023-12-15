package com.alja.physician;

import lombok.Getter;

@Getter
public enum PhysicianLogs {

    GET_SPECIALIZATIONS("getting list of all Specializations"),
    ADD_SPECIALIZATION("adding new Physician specialization: {}"),
    UPDATE_SPECIALIZATION("updating physician Specialization name from {} to {}"),
    DELETE_SPECIALIZATION("deleting specialization: {}"),

    REGISTER_NEW_PHYSICIAN("adding new Physician: {} {} - {}"),;

    public final String logMessage;

    PhysicianLogs(String logMessage) {
        this.logMessage = logMessage;
    }

}
