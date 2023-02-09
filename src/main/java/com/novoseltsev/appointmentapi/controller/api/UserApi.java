package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.RegistrationUserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserPasswordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "users", description = "api to interact with teachers and students")
public interface UserApi {

    @Operation(summary = "get user by id", tags = "users")
    UserDto getUserById(@PathVariable Long id);

    @Operation(summary = "get all app users", tags = "users")
    List<UserDto> getUsers();

    @Operation(summary = "activate user account to successfully log in", tags = "users")
    void activateUser(@PathVariable String activationCode);

    @Operation(summary = "user registration", tags = "users")
    ResponseEntity<UserDto> create(@Valid @RequestBody RegistrationUserDto registrationDto);

    @Operation(summary = "update user information", tags = "users")
    UserDto update(@Valid @RequestBody UserDto userDto);

    @Operation(summary = "update user password", tags = "users")
    void updatePassword(@Valid @RequestBody UserPasswordDto passwordDto, @PathVariable Long id);

    @Operation(summary = "set user status as Deleted", tags = "users")
    void setDeleted(@PathVariable Long id);

    @Operation(summary = "delete user by id", tags = "users")
    void deleteById(@PathVariable Long id);
}
