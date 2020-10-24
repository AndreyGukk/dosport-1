package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.time.LocalDate;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации нового Мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequest {

    @NotBlank(message = DATA_NOT_BLANK + "Дата")
    private LocalDate dateEvent;

    @NotBlank(message = DATA_NOT_BLANK + "Время начала")
    private String startTimeEvent;

    private String endTimeEvent;

    @NotBlank(message = DATA_NOT_BLANK + "Вид спорта")
    private String sportTypeTitle;

    @NotBlank(message = DATA_NOT_BLANK + "Игровая площадка")
    private String idSportGround;

    @NotBlank(message = DATA_NOT_BLANK + "Организатор")
    private String idOrganizer;
}
