package com.novoseltsev.appointmentapi.exception.util.exceptionhandler;

import com.novoseltsev.appointmentapi.exception.user.RegistrationUserException;
import com.novoseltsev.appointmentapi.exception.util.ExceptionUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Map<String, String> errors;

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Object> handleValidationException(
            ConstraintViolationException e
    ) {
        errors = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations =
                e.getConstraintViolations();
        constraintViolations.forEach(constraintViolation -> errors.put("error",
                constraintViolation.getMessage() + " " + constraintViolation
                        .getPropertyPath() + " but was " + constraintViolation
                        .getInvalidValue()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        errors = ExceptionUtil.handleValidationErrors(ex);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationUserException.class)
    private ResponseEntity<Object> handleRegistrationUserException(
            RegistrationUserException e
    ) {
        errors = new HashMap<>();
        errors.put("error", "Bad user " + e.getMessage() + " in registration");
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
