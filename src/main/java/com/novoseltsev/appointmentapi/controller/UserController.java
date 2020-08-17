package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.RegistrationUserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserPasswordDto;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/appointments-api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return UserDto.fromUser(userService.findById(id));
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAll().stream().map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> create(
            @Valid @RequestBody RegistrationUserDto registrationDto
    ) {
        User user = userService.create(registrationDto.toUser());
        return new ResponseEntity<>(UserDto.fromUser(user),
                HttpStatus.CREATED);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UserDto userDto) {
        return UserDto.fromUser(userService.update(userDto.toUser()));
    }

    @PutMapping("/password/{id}")
    public void updatePassword(
            @Valid @RequestBody UserPasswordDto passwordDto,
            @PathVariable Long id
    ) {
        userService.updatePassword(id, passwordDto.getOldPassword(),
                passwordDto.getNewPassword());
    }

    @DeleteMapping("/{id}")
    public void markAsDeleted(@PathVariable Long id) {
        userService.markAsDeleted(id);
    }

    @DeleteMapping("/deletion/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}