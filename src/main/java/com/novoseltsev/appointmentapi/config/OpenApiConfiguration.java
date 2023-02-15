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

    @Bean
    public OpenAPI openAPI(@Value("${server.base.url}") String serverUrl) {
        return new OpenAPI()
            .info(new Info()
                .title("University Teacher-Student Appointment API")
                .description("API to create custom payable meetings between teacher and student")
                .version("1.0")
            )
            .servers(Collections.singletonList(new Server().url(serverUrl)))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .name("bearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer_")
                    .description("Provide the JWT token. JWT token can be obtained from the Login API. For testing, use the credentials <strong>john/password</strong>")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)));
    }
}
