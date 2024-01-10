package com.alja.common.enums;

public enum VisitStatus {
    AVAILABLE,
    RESERVED,
    COMPLETED,
    UNREALIZED;

    public static boolean isValid(String status) {
        for (VisitStatus value : values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}
