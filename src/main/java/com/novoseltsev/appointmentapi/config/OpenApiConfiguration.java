package com.novoseltsev.appointmentapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_NAME = "JWT Authorization";

    @Bean
    public OpenAPI openAPI(@Value("${server.base.url}") String serverBaseUrl,
                           @Value("${server.servlet.context-path}") String serverContextPath,
                           @Value("${default.auth.username}") String username,
                           @Value("${default.auth.password}") String password) {
        return new OpenAPI()
            .info(new Info()
                .title("University Teacher-Student Appointment API")
                .description("API to create custom payable meetings between teacher and student")
                .version("1.0")
            )
            .servers(Collections.singletonList(new Server().url(serverBaseUrl + serverContextPath)))
            .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION_NAME))
            .components(new Components()
                .addSecuritySchemes(AUTHORIZATION_NAME, new SecurityScheme()
                    .name(AUTHORIZATION_HEADER)
                    .type(SecurityScheme.Type.APIKEY)
                    .scheme("bearer_")
                    .description("Provide the JWT token. JWT token can be obtained from the Authentication API. "
                        + "For testing, use the credentials <strong>" + username + "/" + password + "</strong>")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)));
    }
}
