package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.*;

/**
 * Запрос для регистрации нового Пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Пользователя")
public class UserRequest {

    @Email
    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Адрес эл. почты")
    @ApiModelProperty(notes = "Email пользователя, от 4 до 50 символов",
            dataType = "String", example = "abc@mail.ru", required = true, position = 0)
    private String username;

    @Size(min=2, max=50, message = INVALID_FIRSTNAME_LENGTH)
    @ApiModelProperty(notes = "Имя пользователя, от 2 до 50 символов",
            dataType = "String",  example = "Иван", required = true, position = 1)
    private String firstName;

    @Size(min=2, max=100, message = INVALID_LASTNAME_LENGTH)
    @ApiModelProperty(notes = "Фамилия пользователя, от 2 до 100 символов",
            dataType = "String",  example = "Иванов",  required = false, position = 2)
    private String lastName;

    @Size(min=6, max=25, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Пароль")
    @ApiModelProperty(notes = "Пароль пользователя, от 6 до 25 символов",
            dataType = "String", required = true, position = 3)
    private String password;

    @Size(min=6, max=25, message = INVALID_PASSWORD_CONFIRM_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Пароль")
    @ApiModelProperty(notes = "Подтверждение пароля, от 6 до 25 символов",
            dataType = "String", required = true, position = 4)
    private String passwordConfirm;
}
