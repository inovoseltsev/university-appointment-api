package com.novoseltsev.appointmentapi.service;

import org.springframework.stereotype.Service;

@Service
public interface MailSenderService {

    void sendMessage(String to, String subject, String text);
}
