package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.AuthenticationDto;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.security.jwt.JwtProvider;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/appointments/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@Valid @RequestBody AuthenticationDto authDto) {

        try {
            String login = authDto.getLogin();
            String password = authDto.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password));
            User user = userService.findByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException("User with login: " + login
                        + " not found!");
            }
            String token = jwtProvider.createToken(String.valueOf(user.getId()),
                    user.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password!");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
