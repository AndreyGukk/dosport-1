package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.dosport.entities.Messages.*;

/**
 * Запрос на изменение пароля
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordRequest {

    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String oldPassword;

    @Size(min=4, max=20, message = PASSWORD_LENGTH)
    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String newPassword;

    @Size(min=4, max=20, message = PASSWORD_LENGTH)
    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String newPasswordConfirm;
}
