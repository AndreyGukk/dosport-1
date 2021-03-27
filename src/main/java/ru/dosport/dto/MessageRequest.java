package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_BLANK;
import static ru.dosport.helpers.SwaggerMessages.PAR_MESSAGE_TEXT;

/**
 * Запрос для регистрации нового Сообщения
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Сообщения")
public class MessageRequest {

    @Size(min = 5, max = 255)
    @NotBlank(message = DATA_NOT_BLANK + PAR_MESSAGE_TEXT)
    @ApiModelProperty(notes = PAR_MESSAGE_TEXT + "от 5 до 255 символов",
            dataType = "String", required = true, position = 0)
    private String text;
}
