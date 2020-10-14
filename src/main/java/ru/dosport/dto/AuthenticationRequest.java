package ru.dosport.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.dosport.entities.Messages.MUST_BE_NOT_BLANK;
import static ru.dosport.entities.Messages.MUST_BE_NOT_NULL;

/**
 * Запрос авторизации
 */
@Data
public class AuthenticationRequest {

    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String username;

    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String password;
}
