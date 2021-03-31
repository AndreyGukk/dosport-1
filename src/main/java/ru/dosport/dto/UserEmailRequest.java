package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_BLANK;
import static ru.dosport.helpers.InformationMessages.INVALID_EMAIL_LENGTH;
import static ru.dosport.helpers.SwaggerMessages.PAR_EMAIL;

/**
 * Запрос для регистрации нового Пользователя по никнейму и адресу электронной почты
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Пользователя по адресу электронной почты")
public class UserEmailRequest extends UserRequest {

    @Size(min = 6, max = 100, message = INVALID_EMAIL_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + PAR_EMAIL)
    @Email(message = INVALID_EMAIL_LENGTH)
    @ApiModelProperty(notes = PAR_EMAIL + ", от 6 до 100 символов",
            dataType = "String", required = true, position = 1)
    private String email;
}
