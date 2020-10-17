package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.dosport.entities.Messages.*;

/**
 * Запрос авторизации
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    @NotBlank(message = USERNAME_NOT_BLANK)
    private String username;

    @NotBlank(message = PASSWORD_NOT_BLANK)
    private String password;
}
