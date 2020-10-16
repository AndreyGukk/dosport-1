package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.entities.Messages.*;

/**
 * Dto представление сущности Пользователь
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;

    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = USERNAME_NOT_BLANK)
    private String username;

    private String birthdayDate;

    @NotBlank(message = FIRSTNAME_NOT_BLANK)
    private String firstName;

    private String lastName;

    private String gender;

    private String info;

    private String photoLink;
}
