package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.dosport.entities.Messages.*;

/**
 * Запрос для регистрации нового Пользователя
 */
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @Size(min=4, max=50, message = INVALID_USERNAME)
    @NotBlank(message = NOT_BLANK)
//    @Email(message = EMAIL_FORMAT_ERROR)
    private String username;

    private String firstName;

    private String lastName;

    @Size(min=4, max=20, message = INVALID_PASSWORD)
    @NotBlank(message = NOT_BLANK)
    private String password;

    private String passwordConfirm;
}
