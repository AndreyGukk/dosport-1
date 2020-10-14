package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.dosport.entities.Messages.*;

/**
 * DTO представление сущности Пользователь
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;

    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String username;

    private String creationDate;

    @NotNull(message = MUST_BE_NOT_NULL)
    @NotBlank(message = MUST_BE_NOT_BLANK)
    private String firstName;

    private String lastName;

    @NotNull(message = MUST_BE_NOT_NULL)
    @Email(message = MUST_BE_EMAIL)
    private String email;

    private String photoLink;
}
