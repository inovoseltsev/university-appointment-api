package com.novoseltsev.appointmentapi.config;

import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.security.jwt.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfigurer jwtConfigurer;

    private static final String[] TEACHER_ENDPOINTS = new String[]{
        "/users/teachers/**",
        "/appointments/confirmation/**",
        "/appointments/revocation/**"
    };

    private static final String[] STUDENT_ENDPOINTS = new String[]{
        "/users/students/**",
        "/appointments/creation",
        "/appointments/updating",
        "/appointments/cancel-reservation/**"
    };

    private static final String[] OPEN_ENDPOINTS = new String[]{
        "/auth/**",
        "/users/registration",
        "/users/activation/**",
        "/appointments/cancel-reservation/code/**",
        "/appointments/confirmation/code/**",
        "/appointments/revocation/code/**",

        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .httpBasic()
            .disable()
            .csrf()
            .and()
            .cors()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(OPEN_ENDPOINTS).permitAll()
            .antMatchers(TEACHER_ENDPOINTS).hasAuthority(UserRole.TEACHER.name())
            .antMatchers(STUDENT_ENDPOINTS).hasAuthority(UserRole.STUDENT.name())
            .anyRequest().authenticated()
            .and()
            .apply(jwtConfigurer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
