package com.novoseltsev.appointmentapi.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InappropriateUserRoleException extends RuntimeException {

    public InappropriateUserRoleException() {
    }

    public InappropriateUserRoleException(String message) {
        super(message);
    }
}
