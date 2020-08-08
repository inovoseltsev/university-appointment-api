package com.novoseltsev.appointmentapi.security.jwt;

import com.novoseltsev.appointmentapi.domain.entity.User;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
            return new JwtUser(user);
    }

}
