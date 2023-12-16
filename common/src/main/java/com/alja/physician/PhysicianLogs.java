package com.alja.physician;

import lombok.Getter;

@Getter
public enum PhysicianLogs {

    REGISTER_NEW_PHYSICIAN("adding new Physician: {} {} - {}"),
    GET_ALL_PHYSICIANS("getting list of all Physicians"),
    GET_PHYSICIAN_BY_ID("getting Physician with id: {}"),
    UPDATE_PHYSICIAN("updating Physician with id: {}"),
    DELETE_PHYSICIAN_BY_ID("deleting Physician with id: {}"),

    GET_SPECIALIZATIONS("getting list of all Specializations"),
    ADD_SPECIALIZATION("adding new Physician specialization: {}"),
    UPDATE_SPECIALIZATION("updating physician Specialization name from {} to {}"),
    DELETE_SPECIALIZATION("deleting specialization: {}");



    public final String logMessage;

    PhysicianLogs(String logMessage) {
        this.logMessage = logMessage;
    }

}
