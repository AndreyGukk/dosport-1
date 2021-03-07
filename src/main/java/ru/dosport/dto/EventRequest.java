package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
@ApiModel(description = "Запрос для регистрации нового Мероприятия")
public class EventRequest {

    @FutureOrPresent
    @NotNull(message = DATA_NOT_BLANK + "Дата")
    @DateTimeFormat(pattern="yyyy.MM.dd")
    @ApiModelProperty(notes = "Дата проведения Мероприятия", dataType = "LocalDate", example = "2007-12-03", required = true, position = 0)
    private LocalDate dateEvent;

    @NotNull(message = DATA_NOT_BLANK + "Время начала")
    @DateTimeFormat(pattern="HH:mm")
    @ApiModelProperty(notes = "Время начала проведения Мероприятия", dataType = "LocalTime", example = "10:15", required = true, position = 1)
    private LocalTime startTimeEvent;

    @DateTimeFormat(pattern="HH:mm")
    @ApiModelProperty(notes = "Время окончания проведения Мероприятия", dataType = "LocalTime", example = "10:15", required = true, position = 2)
    private LocalTime endTimeEvent;

    @NotEmpty(message = DATA_NOT_BLANK + "Вид спорта")
    @ApiModelProperty(notes = "Вид спорта", dataType = "String", example = "фехтование", required = true, position = 3)
    private String sportTypeTitle;

    @NotEmpty(message = DATA_NOT_BLANK + "Игровая площадка")
    @ApiModelProperty(notes = "Место проведения Мероприятия", dataType = "Long", example = "1", required = true, position = 4)
    private String sportGroundId;

    @NotEmpty(message = DATA_NOT_BLANK + "Организатор")
    @ApiModelProperty(notes = "Id организатора", dataType = "Long", example = "1", required = true, position = 5)
    private Long organizerId;
}
