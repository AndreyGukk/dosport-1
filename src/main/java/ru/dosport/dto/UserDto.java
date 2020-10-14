package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.dosport.entities.Messages.*;

/**
 * DTO представление сущности Пользователь
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;

    @Size(min=4, max=40, message = INVALID_USERNAME)
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
//    @Email(message = EMAIL_FORMAT_ERROR)
    private String username;

    private String birthday;

    private String gender;

    private String info;

    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String firstName;

    private String lastName;

    private String photoLink;
}
