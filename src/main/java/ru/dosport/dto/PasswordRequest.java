package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.InformationMessages.*;

/**
 * Запрос на изменение пароля
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос на изменение пароля")
public class PasswordRequest {

    @Size(min = 6, max = 25, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Старый пароль")
    @ApiModelProperty(notes = "Старый пароль пользователя",
            dataType = "String", required = true, position = 0)
    private String oldPassword;

    @Size(min = 6, max = 25, message = INVALID_PASSWORD_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Новый пароль, от 6 до 25 символов")
    @ApiModelProperty(notes = "Новый пароль пользователя",
            dataType = "String", required = true, position = 1)
    private String newPassword;

    @Size(min = 6, max = 25, message = INVALID_PASSWORD_CONFIRM_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Подтверждение пароля")
    @ApiModelProperty(notes = "Подтверждение пароля, от 6 до 25 символов",
            dataType = "String", required = true, position = 2)
    private String newPasswordConfirm;
}
