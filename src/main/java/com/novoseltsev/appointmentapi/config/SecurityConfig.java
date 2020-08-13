package com.novoseltsev.appointmentapi.config;

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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfigurer jwtConfigurer;

    private static final String TEACHER_ENDPOINTS =
            "/api/v1/appointments/users/teachers/**";
    private static final String STUDENT_ENDPOINTS =
            "/api/v1/appointments/users/students/**";
    private static final String[] FREE_ENDPOINT =
            new String[]{"/api/v1/appointments/auth/**",
                    "/api/v1/appointments/users/registration"};

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
                .antMatchers(FREE_ENDPOINT).permitAll()
                .antMatchers(TEACHER_ENDPOINTS).hasAuthority("TEACHER")
                .antMatchers(STUDENT_ENDPOINTS).hasAuthority("STUDENT")
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
