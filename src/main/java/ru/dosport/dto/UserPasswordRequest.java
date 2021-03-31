package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.InformationMessages.*;

/**
 * Запрос для регистрации нового Пользователя по никнейму и паролю
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Пользователя")
public class UserPasswordRequest extends UserRequest {

    @Size(min = 6, max = 25, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Пароль")
    @ApiModelProperty(notes = "Пароль пользователя, от 6 до 25 символов",
            dataType = "String", required = true, position = 1)
    private String password;

    @Size(min = 6, max = 25, message = INVALID_PASSWORD_CONFIRM_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Подтверждение пароля")
    @ApiModelProperty(notes = "Подтверждение пароля, от 6 до 25 символов",
            dataType = "String", required = true, position = 2)
    private String passwordConfirm;
}
