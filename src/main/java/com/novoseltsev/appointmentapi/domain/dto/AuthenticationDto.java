package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.LOGIN_OR_PASSWORD_ERROR;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.LOGIN_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.PASSWORD_PATTERN;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationDto {

    @NotBlank(message = LOGIN_OR_PASSWORD_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_OR_PASSWORD_ERROR)
    private String login;

    @NotBlank(message = LOGIN_OR_PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = LOGIN_OR_PASSWORD_ERROR)
    private String password;
}
