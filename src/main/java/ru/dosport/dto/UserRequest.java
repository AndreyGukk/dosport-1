package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.SwaggerMessages.PAR_EMAIL;
import static ru.dosport.helpers.SwaggerMessages.PAR_USERNAME;

/**
 * Общий класс запросов для регистрации нового Пользователя
 */
@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Общий класс запросов для регистрации нового Пользователя")
public abstract class UserRequest {

    @Size(min = 4, max = 50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + PAR_USERNAME)
    @ApiModelProperty(notes = PAR_USERNAME + ", от 4 до 50 символов",
            dataType = "String", example = "Nickname", required = true, position = 0)
    private String username;
}
