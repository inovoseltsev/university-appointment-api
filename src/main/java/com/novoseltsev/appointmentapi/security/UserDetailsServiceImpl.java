package com.novoseltsev.appointmentapi.security;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.security.jwt.JwtUser;
import com.novoseltsev.appointmentapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User loadedUser = userService.findByLogin(login);
        if (loadedUser == null) {
            throw new UsernameNotFoundException("User with login: " + login
                    + " not found!");
        }
        return new JwtUser(loadedUser);
    }
}
