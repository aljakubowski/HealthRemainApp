package com.alja.physician;

public enum PhysicianResponseMessage {
    //fixme to delete?
    PHYSICIAN_REGISTER_SUCCESS("Successfully registered new physician");

    public final String message;

    PhysicianResponseMessage(String logMessage) {
        this.message = logMessage;
    }

    @Override
    public String toString() {
        return message;
    }
}
