package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;
import static ru.dosport.helpers.Messages.INVALID_USERNAME_LENGTH;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Сообщение о спортивном событии для пользователя")
public class EventMessageDto {

    @ApiModelProperty(notes = "Id оообщения", dataType = "Long", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "Id события", dataType = "Long", example = "1", required = true, position = 1)
    private Long eventId;

    @ApiModelProperty(notes = "Id пользователя", dataType = "Long", example = "1", required = true, position = 2)
    private Long userId;

    @Email
    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @ApiModelProperty(notes = "Email пользователя", dataType = "String", example = "abc@mail.ru", required = true, position = 3)
    private String userName;

    @ApiModelProperty(notes = "Текст сообщения пользователя", dataType = "String", required = true, position = 4)
    private String text;
}
