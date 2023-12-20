package com.spme.fantasolver.entity;

public class RoleLimitExceededException extends RoleException {
    public RoleLimitExceededException(String message) {
        super(message);
    }
}
