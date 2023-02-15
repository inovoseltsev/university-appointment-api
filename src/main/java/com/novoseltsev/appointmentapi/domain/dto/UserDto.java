package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.appointmentapi.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.Data;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.EMAIL_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.FIRST_NAME_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.LAST_NAME_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.LOGIN_ERROR;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.EMAIL_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.LOGIN_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.NAME_PATTERN;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @NotNull
    @Positive
    @Min(value = 1)
    @Schema(example = "123", required = true)
    private Long id;

    @NotBlank(message = FIRST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    @Schema(example = "Illia", required = true)
    private String firstName;

    @NotBlank(message = LAST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
    @Schema(example = "Novoseltsev", required = true)
    private String lastName;

    @NotBlank(message = LOGIN_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    @Schema(example = "mylogin", required = true)
    private String login;

    @NotBlank(message = EMAIL_ERROR)
    @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_ERROR)
    @Schema(example = "illianovoseltsev@illia.com", required = true)
    private String email;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
