package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации нового Мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequest {

    @NotBlank(message = DATA_NOT_BLANK + "Дата и время начала")
    private String startDateTime;

    @NotBlank(message = DATA_NOT_BLANK + "Дата и время завершения")
    private String stopDateTime;

    @NotBlank(message = DATA_NOT_BLANK + "Вид спорта")
    private SportTypeDto sportType;

    @NotBlank(message = DATA_NOT_BLANK + "Игровая площадка")
    private FieldDto field;
}
