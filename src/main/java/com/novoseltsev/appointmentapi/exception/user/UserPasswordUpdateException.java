package com.novoseltsev.appointmentapi.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserPasswordUpdateException extends RuntimeException {

    public UserPasswordUpdateException() {
    }

    public UserPasswordUpdateException(String message) {
        super(message);
    }

}
