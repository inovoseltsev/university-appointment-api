package com.novoseltsev.appointmentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserPasswordUpdateException extends RuntimeException {

    public UserPasswordUpdateException() {
    }

    public UserPasswordUpdateException(String message) {
        super(message);
    }

}
