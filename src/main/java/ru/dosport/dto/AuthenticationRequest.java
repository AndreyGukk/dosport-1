package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static ru.dosport.helpers.Messages.*;

/**
 * Запрос авторизации
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос авторизации")
public class AuthenticationRequest {

    @NotBlank(message = DATA_NOT_BLANK + "Адрес эл. почты")
    @ApiModelProperty(notes = "Email пользователя",
            dataType = "String", example = "ivanov@gmail.com", required = true, position = 0)
    private String email;

    @NotBlank(message = DATA_NOT_BLANK + "Пароль")
    @ApiModelProperty(notes = "Пароль",
            dataType = "String", example = "aaaaaa", required = true, position = 1)
    private String password;
}
