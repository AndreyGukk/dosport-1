package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации нового Сообщения
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Сообщения")
public class EventMessageRequest {

    @Size(min = 5, max = 300)
    @NotBlank(message = DATA_NOT_BLANK + "Текст сообщения")
    @ApiModelProperty(notes = "Текст оообщения", dataType = "String", required = true, position = 0)
    private String text;
}
