package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.PASSWORD_ERROR;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.PASSWORD_PATTERN;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPasswordDto {

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    @Schema(example = "Password12", minLength = 4, maxLength = 16, required = true)
    private String oldPassword;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    @Schema(example = "Password123", minLength = 4, maxLength = 16, required = true)
    private String newPassword;
}
