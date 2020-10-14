package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.dosport.entities.Messages.*;

/**
 * Запрос для регистрации нового Пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @Size(min=4, max=20, message = USERNAME_LENGTH)
    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    @Size(min=4, max=20, message = PASSWORD_LENGTH)
    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String password;

    private String passwordConfirm;
}
