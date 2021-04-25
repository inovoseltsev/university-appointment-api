package com.novoseltsev.appointmentapi.config;

import com.novoseltsev.appointmentapi.security.jwt.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfigurer jwtConfigurer;

    private static final String[] TEACHER_ENDPOINTS = new String[]{
            "/api/v1/appointments-api/users/teachers/**",
            "/api/v1/appointments-api/appointments/confirmation/**",
            "/api/v1/appointments-api/appointments/revocation/**"};
    private static final String[] STUDENT_ENDPOINTS = new String[]{
            "/api/v1/appointments-api/users/students/**",
            "/api/v1/appointments-api/appointments/creation",
            "/api/v1/appointments-api/appointments/updating",
            "/api/v1/appointments-api/appointments/cancel-reservation/**"};
    private static final String[] FREE_ENDPOINTS = new String[]{
            "/api/v1/appointments-api/auth/**",
            "/api/v1/appointments-api/users/registration",
            "/api/v1/appointments-api/users/activation/**",
            "/api/v1/appointments-api/appointments/cancel-reservation/code/**",
            "/api/v1/appointments-api/appointments/confirmation/code/**",
            "/api/v1/appointments-api/appointments/revocation/code/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(FREE_ENDPOINTS).permitAll()
                .antMatchers(TEACHER_ENDPOINTS).hasAuthority("TEACHER")
                .antMatchers(STUDENT_ENDPOINTS).hasAuthority("STUDENT")
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfigurer);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**");
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
