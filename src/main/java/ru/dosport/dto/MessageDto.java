package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static ru.dosport.helpers.SwaggerMessages.*;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Сообщение")
public class MessageDto {

    @ApiModelProperty(notes = PAR_MESSAGE_ID,
            dataType = "Long", example = "1", required = true, position = 0)
    private Long messageId;

    @ApiModelProperty(notes = PAR_EVENT_ID,
            dataType = "Long", example = "1", required = true, position = 1)
    private Long eventId;

    @ApiModelProperty(notes = PAR_USER_ID,
            dataType = "Long", example = "1", required = true, position = 2)
    private Long userId;

    @ApiModelProperty(notes = PAR_USERNAME,
            dataType = "String", example = "Nickname", required = true, position = 3)
    private String username;

    @ApiModelProperty(notes = PAR_USER_PHOTO,
            dataType = "String", example = "myphoto.png", position = 4)
    private String photoLink;

    @ApiModelProperty(notes = PAR_MESSAGE_TEXT,
            dataType = "String", required = true, position = 5)
    private String text;
}
