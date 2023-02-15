package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserPasswordDto;
import com.novoseltsev.appointmentapi.domain.dto.UserRegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "user", description = "User API")
public interface UserApi {

    @Operation(summary = "Get user by id", tags = "user")
    @ApiResponse(
        responseCode = "200",
        description = "User",
        content = {
            @Content(
                schema = @Schema(implementation = UserDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    UserDto getUserById(
        @Parameter(description = "Id of user which will be returned", required = true) Long id
    );

    @Operation(summary = "Get all app users", tags = "user")
    @ApiResponse(
        responseCode = "200",
        description = "List of all application users",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    List<UserDto> getUsers();

    @Operation(summary = "Activate user account to successfully log in", tags = "user")
    void activateUser(
        @Parameter(description = "Unique activation code that user receives by email", required = true) String activationCode
    );

    @Operation(summary = "User registration", tags = "user")
    @RequestBody(
        description = "User registration info",
        required = true,
        content = @Content(
            schema = @Schema(implementation = UserRegistrationDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "201",
        description = "Created user",
        content = {
            @Content(
                schema = @Schema(implementation = UserDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    ResponseEntity<UserDto> create(UserRegistrationDto registrationDto);

    @Operation(summary = "Update user information", tags = "user")
    @RequestBody(
        description = "Updated user",
        required = true,
        content = @Content(
            schema = @Schema(implementation = UserDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "Updated user",
        content = {
            @Content(
                schema = @Schema(implementation = UserDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    UserDto update(UserDto userDto);

    @Operation(summary = "Update user password", tags = "user")
    @RequestBody(
        description = "Old password to compare and new password to update",
        required = true,
        content = @Content(
            schema = @Schema(implementation = UserPasswordDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    void updatePassword(UserPasswordDto passwordDto, @Parameter(description = "User id", required = true) Long id);

    @Operation(summary = "Set user status as deleted", tags = "user")
    void setDeleted(@Parameter(description = "User id", required = true) Long id);

    @Operation(summary = "Delete user by id", tags = "user")
    void deleteById(@Parameter(description = "User id", required = true) Long id);
}
