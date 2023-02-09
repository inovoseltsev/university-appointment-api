package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.AuthenticationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.MediaType;

@Tag(name = "authentication", description = "api to get JWT and authorize in app")
public interface AuthenticationApi {

    @Operation(summary = "send login and password and return JWT", tags = "authentication")
    @RequestBody(
        required = true,
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = AuthenticationDto.class)
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "Json Web Token (JWT)",
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = Map.class))
        })
    Map<Object, Object> login(AuthenticationDto authDto);
}
