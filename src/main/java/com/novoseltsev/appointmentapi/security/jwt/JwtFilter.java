package com.novoseltsev.appointmentapi.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novoseltsev.appointmentapi.exception.auth.JwtAuthenticationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


@Component
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token =
                    jwtProvider.resolveToken((HttpServletRequest) request);
            if (token != null && jwtProvider.isValid(token)) {
                Authentication auth = jwtProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            chain.doFilter(request, response);
        } catch (JwtAuthenticationException | IllegalArgumentException e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            httpServletResponse.getWriter().write(new ObjectMapper()
                    .writeValueAsString(error));
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
        }
    }
}
