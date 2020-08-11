package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.RegistrationUserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserPasswordDto;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import com.novoseltsev.appointmentapi.exception.UserNotFoundException;
import com.novoseltsev.appointmentapi.exception.util.ExceptionUtil;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/appointments/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAll().stream().map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserDto.fromUser(user));
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> create(
            @Valid @RequestBody RegistrationUserDto registrationUserDto) {
        String login = registrationUserDto.getLogin();
        String email = registrationUserDto.getEmail();
        String password = registrationUserDto.getPassword();
        String repeatedPassword = registrationUserDto.getRepeatedPassword();
        if (!password.equals(repeatedPassword)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (!loginAndEmailAreUniq(login, email)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        User user = userService.create(registrationUserDto.toUser());
        return new ResponseEntity<>(UserDto.fromUser(user),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto) {
        User user = userService.findById(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user = userService.update(user);
        return ResponseEntity.ok(UserDto.fromUser(user));
    }

    @PutMapping("/password/{id}")
    public ResponseEntity updatePassword(@Valid @RequestBody UserPasswordDto passwordDto,
                                         @PathVariable Long id) {
        User user = userService.findById(id);
        if (!passwordsCoincide(passwordDto,
                user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<UserDto> markAsDeleted(@Valid @RequestBody UserDto userDto) {
        User user = userService.findById(userDto.getId());
        user.setStatus(UserStatus.DELETED);
        user = userService.update(user);
        return ResponseEntity.ok(UserDto.fromUser(user));
    }

    @DeleteMapping("/deletion/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
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

    private boolean passwordsCoincide(UserPasswordDto passwordDto,
                                      String userPassword) {
        String newPassword = passwordDto.getNewPassword();
        String repeatedPassword = passwordDto.getRepeatedPassword();
        if (!passwordEncoder.matches(passwordDto.getOldPassword(),
                userPassword)) {
            return false;
        } else return newPassword.equals(repeatedPassword);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(
            MethodArgumentNotValidException e) {
        return ExceptionUtil.handleValidationErrors(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException() {
        Map<String, String> error = new HashMap<>();
        error.put("error", "User with such id doesn't exist");
        return error;
    }
}