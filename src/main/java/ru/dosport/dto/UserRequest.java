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
@ApiModel(description = "Запрос для регистрации нового Пользователя.")
public class UserRequest {

    @Email
    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Адрес эл. почты")
    @ApiModelProperty(notes = "Emeil пользователя!", dataType = "String", example = "abc@mail.ru", required = true, position = 0)
    private String username;

    @NotBlank(message = DATA_NOT_BLANK + "Имя")
    @ApiModelProperty(notes = "Имя пользователя", dataType = "String",  example = "Иван", required = true, position = 1)
    private String firstName;

    @ApiModelProperty(notes = "Фамилия пользователя", dataType = "String",  example = "Иванов",  required = true, position = 2)
    private String lastName;

    @Size(min=4, max=20, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Пароль")
    @ApiModelProperty(notes = "Пароль пользователя", dataType = "String", required = true, position = 3)
    private String password;

    @ApiModelProperty(notes = "Подтверждение пароля", dataType = "String", required = true, position = 4)
    private String passwordConfirm;
}
