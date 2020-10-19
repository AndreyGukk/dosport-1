package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.*;

/**
 * Запрос на изменение пароля
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordRequest {

    @Size(min=4, max=20, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Текущий пароль")
    private String oldPassword;

    @Size(min=4, max=20, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Новый пароль")
    private String newPassword;

    private String newPasswordConfirm;
}
