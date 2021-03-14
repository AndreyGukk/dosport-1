package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.INVALID_USERNAME_LENGTH;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Сообщение о спортивном событии для пользователя")
public class EventMessageDto {

    @ApiModelProperty(notes = "Id сообщения",
            dataType = "Long", example = "1", required = true, position = 0)
    private Long messageId;

    @ApiModelProperty(notes = "Id события",
            dataType = "Long", example = "1", required = true, position = 1)
    private Long eventId;

    @ApiModelProperty(notes = "Id пользователя",
            dataType = "Long", example = "1", required = true, position = 2)
    private Long userId;

    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @ApiModelProperty(notes = "Никнейм пользователя",
            dataType = "String", example = "Nickname", required = true, position = 3)
    private String username;

    @ApiModelProperty(notes = "Ссылка на файл фото, до 150 символов",
            dataType = "String", example = "myphoto.png", position = 4)
    @Size(max = 150)
    private String photoLink;

    @ApiModelProperty(notes = "Текст сообщения пользователя",
            dataType = "String", required = true, position = 5)
    private String text;
}
