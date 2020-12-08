package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации нового Мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequest {

    @FutureOrPresent
    @NotNull(message = DATA_NOT_BLANK + "Дата")
    private LocalDate dateEvent;

    @NotNull(message = DATA_NOT_BLANK + "Время начала")
    private LocalTime startTimeEvent;

    private LocalTime endTimeEvent;

    @NotEmpty(message = DATA_NOT_BLANK + "Вид спорта")
    private String sportTypeTitle;

    @NotEmpty(message = DATA_NOT_BLANK + "Игровая площадка")
    private String sportGroundId;

    @NotEmpty(message = DATA_NOT_BLANK + "Организатор")
    private String organizerId;
}
