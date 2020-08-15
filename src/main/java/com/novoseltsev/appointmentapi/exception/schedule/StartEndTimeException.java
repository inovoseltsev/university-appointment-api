package com.novoseltsev.appointmentapi.exception.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StartEndTimeException extends RuntimeException {

    public StartEndTimeException() {
    }

    public StartEndTimeException(String message) {
        super(message);
    }
}
