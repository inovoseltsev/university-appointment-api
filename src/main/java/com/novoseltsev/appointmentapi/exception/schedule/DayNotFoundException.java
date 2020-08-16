package com.novoseltsev.appointmentapi.exception.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DayNotFoundException extends RuntimeException{

    public DayNotFoundException() {
    }

    public DayNotFoundException(String message) {
        super(message);
    }
}
