package com.novoseltsev.appointmentapi.exception.time;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IncorrectTimeIntervalException extends RuntimeException {

    public IncorrectTimeIntervalException() {
    }

    public IncorrectTimeIntervalException(String message) {
        super(message);
    }
}
