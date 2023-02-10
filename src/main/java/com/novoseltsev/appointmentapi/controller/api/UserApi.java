package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.AuthenticationDto;
import com.novoseltsev.appointmentapi.domain.dto.RegistrationUserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserPasswordDto;
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

@Tag(name = "users", description = "api to interact with teachers and students")
public interface UserApi {

    @Operation(summary = "get user by id", tags = "users")
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

    @Operation(summary = "get all app users", tags = "users")
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

    @Operation(summary = "activate user account to successfully log in", tags = "users")
    void activateUser(
        @Parameter(description = "Unique activation code that user receives by email", required = true) String activationCode
    );

    @Operation(summary = "user registration", tags = "users")
    @RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = AuthenticationDto.class),
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
    ResponseEntity<UserDto> create(RegistrationUserDto registrationDto);

    @Operation(summary = "update user information", tags = "users")
    @RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = UserDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "updated user",
        content = {
            @Content(
                schema = @Schema(implementation = UserDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    UserDto update(UserDto userDto);

    @Operation(summary = "update user password", tags = "users")
    @RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = UserPasswordDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    void updatePassword(UserPasswordDto passwordDto, @Parameter(description = "User id", required = true) Long id);

    @Operation(summary = "set user status as deleted", tags = "users")
    void setDeleted(@Parameter(description = "User id", required = true) Long id);

    @Operation(summary = "delete user by id", tags = "users")
    void deleteById(@Parameter(description = "User id", required = true) Long id);
}
