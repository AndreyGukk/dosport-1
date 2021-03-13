package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации нового Мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Мероприятия")
public class EventRequest {

    @NotNull(message = DATA_NOT_BLANK + "Дата и время начала")
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
    @ApiModelProperty(notes = "Дата и время начала проведения Мероприятия",
            dataType = "LocalDateTime", example = "2020-12-03 10:15", required = true, position = 0)
    private LocalDateTime startDateTime;

    @NotNull(message = DATA_NOT_BLANK + "Дата и время начала")
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
    @ApiModelProperty(notes = "Дата и время окончания проведения Мероприятия",
            dataType = "LocalDateTime", example = "2020-12-03 10:15", required = true, position = 1)
    private LocalDateTime endDateTime;

    @NotEmpty(message = DATA_NOT_BLANK + "Вид спорта")
    @ApiModelProperty(notes = "Вид спорта",
            dataType = "String", example = "фехтование", required = true, position = 2)
    private String sportTypeTitle;

    @NotEmpty(message = DATA_NOT_BLANK + "Игровая площадка")
    @ApiModelProperty(notes = "Идентификатор спортивной площадки",
            dataType = "Long", example = "1", required = true, position = 3)
    private Long sportGroundId;

    @ApiModelProperty(notes = "Описание мероприятия, до 150 символов",
            dataType = "String", example = "Классное мероприятие", required = true, position = 4)
    private String description;

    @NotEmpty(message = DATA_NOT_BLANK + "Приватность мероприятия")
    @ApiModelProperty(notes = "Приватность мероприятия, true - закрытое",
            dataType = "Boolean", example = "true", required = true, position = 5)
    private Boolean isPrivate;

    @PositiveOrZero
    @ApiModelProperty(notes = "Цена участия в мероприятии",
            dataType = "Integer", example = "100", position = 6)
    private Integer price;

    @PositiveOrZero
    @ApiModelProperty(notes = "Максимальное количество участников мероприятия",
            dataType = "Short", example = "10", position = 7)
    private Short maximumMembers;
}
