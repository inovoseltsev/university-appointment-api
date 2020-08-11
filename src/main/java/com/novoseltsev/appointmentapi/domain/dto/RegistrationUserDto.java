package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;


import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.EMAIL_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.FIRST_NAME_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.LAST_NAME_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.LOGIN_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.PASSWORD_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.REPEATED_PASSWORD_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.USER_ROLE_ERROR;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.EMAIL_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.LOGIN_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.NAME_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.PASSWORD_PATTERN;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationUserDto {

    @Column(nullable = false)
    @NotBlank(message = FIRST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    private String firstName;

    @NotBlank(message = LAST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
    private String lastName;

    @NotBlank(message = LOGIN_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    private String login;

    @NotBlank(message = EMAIL_ERROR)
    @Email(regexp = EMAIL_PATTERN, message = EMAIL_ERROR)
    private String email;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String password;

    @NotBlank(message = REPEATED_PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = REPEATED_PASSWORD_ERROR)
    private String repeatedPassword;

    @NotNull(message = USER_ROLE_ERROR)
    private UserRole role;

    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    public static RegistrationUserDto fromUser(User user) {
        RegistrationUserDto userDto = new RegistrationUserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRepeatedPassword(user.getPassword());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
