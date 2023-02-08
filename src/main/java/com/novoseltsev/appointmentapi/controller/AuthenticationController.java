package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.AuthenticationDto;
import com.novoseltsev.appointmentapi.exception.auth.JwtAuthenticationException;
import com.novoseltsev.appointmentapi.exception.user.UserNotFoundException;
import com.novoseltsev.appointmentapi.service.AuthenticationService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public Map<Object, Object> login(@Valid @RequestBody AuthenticationDto authDto) {
        return authenticationService.authenticate(authDto.getLogin(), authDto.getPassword());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UserNotFoundException.class, JwtAuthenticationException.class})
    private Map<String, String> handleInvalidCredentials() {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Login or password is not correct");
        return error;
    }
}
