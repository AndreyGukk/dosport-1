package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос на создание новой сущности Участник мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberRequest {

    @NotBlank(message = DATA_NOT_BLANK + "Статус пользователя")
    @ApiModelProperty(notes = "Статус пользователя как участника события",
            dataType = "String", position = 0)
    private String userStatus;
}
