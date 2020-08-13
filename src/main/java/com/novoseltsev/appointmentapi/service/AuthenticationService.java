package com.novoseltsev.appointmentapi.service;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    Map<Object, Object> authenticate(String login, String password);
}
