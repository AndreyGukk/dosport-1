package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос на создание новой сущности Участник мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberRequest {

    @NotBlank(message = DATA_NOT_BLANK + "Индетификатор пользователя")
    private Long userId;

    @NotBlank(message = DATA_NOT_BLANK + "Индетификатор мероприятия")
    private Long evenId;

    @NotBlank(message = DATA_NOT_BLANK + "Статус пользователя")
    private String userStatus;
}
