package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.RegistrationUserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
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
@RequestMapping("/api/v1/appointments")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.findAll().stream().map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(UserDto.fromUser(user));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<User> create(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        String login = registrationUserDto.getLogin();
        String email = registrationUserDto.getEmail();
        String password = registrationUserDto.getPassword();
        String repeatedPassword = registrationUserDto.getRepeatedPassword();
        if (!password.equals(repeatedPassword)) {
            return new ResponseEntity<>(registrationUserDto.toUser(),
                    HttpStatus.CONFLICT);
        } else if (!loginAndEmailAreUniq(login, email)) {
            return new ResponseEntity<>(registrationUserDto.toUser(),
                    HttpStatus.CONFLICT);
        }
        User user = userService.create(registrationUserDto.toUser());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDto> update(@Valid UserDto userDto) {
        User user = userService.findById(userDto.getId());
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user = userService.update(user);
        return ResponseEntity.ok(UserDto.fromUser(user));
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity markAsDeleted(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        user.setStatus(UserStatus.DELETED);
        userService.update(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        userService.delete(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    private boolean loginAndEmailAreUniq(String login, String email) {
        List<User> users = userService.findAll();
        List<User> coincidedUsers =
                users.stream().filter(user -> {
                    if (user.getLogin().equals(login)) {
                        return true;
                    } else return user.getEmail().equals(email);
                }).collect(Collectors.toList());
        return coincidedUsers.isEmpty();
    }
}