package com.novoseltsev.appointmentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TeacherDetailsNotExistException extends RuntimeException {

    public TeacherDetailsNotExistException() {
    }

    public TeacherDetailsNotExistException(String message) {
        super(message);
    }
}
