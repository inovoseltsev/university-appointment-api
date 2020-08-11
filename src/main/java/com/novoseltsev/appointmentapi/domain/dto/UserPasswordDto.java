package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;


import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.PASSWORD_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.REPEATED_PASSWORD_ERROR;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.PASSWORD_PATTERN;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPasswordDto {

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String oldPassword;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String newPassword;

    @NotBlank(message = REPEATED_PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = REPEATED_PASSWORD_ERROR)
    private String repeatedPassword;
}
