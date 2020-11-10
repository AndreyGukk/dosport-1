package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.dosport.helpers.Messages.*;

/**
 * Запрос авторизации
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    @NotBlank(message = DATA_NOT_BLANK + "Адрес эл. почты")
    private String email;

    @NotBlank(message = DATA_NOT_BLANK + "Пароль")
    private String password;
}
