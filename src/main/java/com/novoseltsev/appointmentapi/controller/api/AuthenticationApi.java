package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.AuthenticationDto;
import java.util.Map;


public interface AuthenticationApi {

    Map<Object, Object> login(AuthenticationDto authDto);
}
