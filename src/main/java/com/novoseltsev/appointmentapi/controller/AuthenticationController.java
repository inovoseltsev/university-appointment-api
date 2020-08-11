package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.AuthenticationDto;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.exception.JwtAuthenticationException;
import com.novoseltsev.appointmentapi.exception.UserNotFoundException;
import com.novoseltsev.appointmentapi.exception.util.ExceptionUtil;
import com.novoseltsev.appointmentapi.security.jwt.JwtProvider;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@Valid @RequestBody AuthenticationDto authDto) {
        String login = authDto.getLogin();
        String password = authDto.getPassword();
        User user = userService.findByLogin(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new JwtAuthenticationException("");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password));
        String token = jwtProvider.createToken(String.valueOf(user.getId()),
                user.getRole());
        Map<Object, Object> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Map<String, String> handleValidationException(
            MethodArgumentNotValidException e) {
        return ExceptionUtil.handleValidationErrors(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UserNotFoundException.class,
            JwtAuthenticationException.class})
    private Map<String, String> handleInvalidCredentials() {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Login or password is not correct");
        return error;
    }
}
