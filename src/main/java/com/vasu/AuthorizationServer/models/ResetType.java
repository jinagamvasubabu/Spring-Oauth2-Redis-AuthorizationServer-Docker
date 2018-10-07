package com.vasu.AuthorizationServer.models;

public enum ResetType {
    CLIENT_CREDENTIALS,
    PASSWORD;

    public static boolean contains(String test) {
        for (ResetType c : ResetType.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
