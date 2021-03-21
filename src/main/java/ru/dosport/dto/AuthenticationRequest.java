package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;
import static ru.dosport.helpers.Messages.INVALID_USERNAME_LENGTH;

/**
 * Запрос авторизации
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос авторизации")
public class AuthenticationRequest {

    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Никнейм")
    @ApiModelProperty(notes = "Никнейм, от 4 до 50 символов",
            dataType = "String", example = "admin", required = true, position = 0)
    private String username;

    @NotBlank(message = DATA_NOT_BLANK + "Пароль")
    @ApiModelProperty(notes = "Пароль",
            dataType = "String", example = "admin", required = true, position = 1)
    private String password;
}
