package ru.dosport.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.dosport.entities.Messages.NOT_BLANK;
import static ru.dosport.entities.Messages.NOT_NULL;

/**
 * Запрос авторизации
 */
@Data
public class AuthenticationRequest {

    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String username;

    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String password;
}
