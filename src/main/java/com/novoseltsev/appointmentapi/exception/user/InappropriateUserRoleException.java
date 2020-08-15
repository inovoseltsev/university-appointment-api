package com.novoseltsev.appointmentapi.exception.user;

public class InappropriateUserRoleException extends RuntimeException {
    public InappropriateUserRoleException() {
    }

    public InappropriateUserRoleException(String message) {
        super(message);
    }
}
