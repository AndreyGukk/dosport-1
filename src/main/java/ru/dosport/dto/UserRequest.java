package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.entities.Messages.*;

/**
 * Запрос для регистрации нового Пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = USERNAME_NOT_BLANK)
    private String username;

    private String firstName;

    private String lastName;

    @Size(min=4, max=20, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = PASSWORD_NOT_BLANK)
    private String password;

    private String passwordConfirm;
}
