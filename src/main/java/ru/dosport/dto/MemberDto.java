package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dto представление сущности Участник меропрятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Участник меропрятия")
public class MemberDto {

    @ApiModelProperty(notes = "Уникальный идентификатор Мероприятия",
            dataType = "Long", example = "1", required = true, position = 0)
    private Long eventId;

    @ApiModelProperty(notes = "Уникальный идентификатор пользователя",
            dataType = "Long", example = "1", required = true, position = 1)
    private Long userId;

    @ApiModelProperty(notes = "Статус пользователя как участника события, варианты: Возможно пойду, Точно пойду, Не выбран",
            example = "Возможно пойду", dataType = "String", position = 2)
    private String userStatus;
}
