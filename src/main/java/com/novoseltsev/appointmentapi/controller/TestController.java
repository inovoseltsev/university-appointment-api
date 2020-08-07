package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public User start() {
        return new User();
    }
}
