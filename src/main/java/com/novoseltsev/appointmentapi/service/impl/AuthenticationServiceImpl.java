package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.exception.auth.JwtAuthenticationException;
import com.novoseltsev.appointmentapi.security.jwt.JwtProvider;
import com.novoseltsev.appointmentapi.service.AuthenticationService;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Override
    public Map<Object, Object> authenticate(String login, String password) {
        User user = userService.findByLogin(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new JwtAuthenticationException("");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password));
        String token = jwtProvider.createToken(String.valueOf(user.getId()),
                user.getRole());
        Map<Object, Object> tokenResponse = new HashMap<>();
        tokenResponse.put("token", token);
        return tokenResponse;
    }
}