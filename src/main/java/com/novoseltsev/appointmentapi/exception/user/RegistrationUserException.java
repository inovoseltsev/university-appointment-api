package com.novoseltsev.appointmentapi.exception.user;

public class RegistrationUserException extends RuntimeException {

    public RegistrationUserException() {
    }

    public RegistrationUserException(String message) {
        super(message);
    }
}
