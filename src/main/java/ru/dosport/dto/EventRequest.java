package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Patterns.*;

/**
 * Запрос для регистрации нового Мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Мероприятия")
public class EventRequest {

    @NotNull(message = DATA_NOT_BLANK + "Дата и время начала")
    @FutureOrPresent(message = DATE_MUST_BE_IN_FUTURE + "Дата и время начала")
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = "Дата и время начала Мероприятия в формате " + LOCAL_DATE_TIME_PATTERN,
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_1, required = true, position = 0)
    private LocalDateTime startDateTime;

    @NotNull(message = DATA_NOT_BLANK + "Дата и время окончания")
    @FutureOrPresent(message = DATE_MUST_BE_IN_FUTURE + "Дата и время окончания")
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = "Дата и время окончания Мероприятия в формате " + LOCAL_DATE_TIME_PATTERN,
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_2, required = true, position = 1)
    private LocalDateTime endDateTime;

    @NotEmpty(message = DATA_NOT_BLANK + "Вид спорта")
    @ApiModelProperty(notes = "Вид спорта",
            dataType = "String", example = "Хоккей", required = true, position = 2)
    private String sportTypeTitle;

    @NotNull(message = DATA_NOT_BLANK + "Идентификатор спортивной площадки")
    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + "Идентификатор спортивной площадки")
    @ApiModelProperty(notes = "Идентификатор спортивной площадки",
            dataType = "Long", example = "1", required = true, position = 3)
    private Long sportGroundId;

    @ApiModelProperty(notes = "Описание мероприятия, до 150 символов",
            dataType = "String", example = "Классное мероприятие", required = true, position = 4)
    private String description;

    @NotNull(message = DATA_NOT_BLANK + "Приватность мероприятия")
    @ApiModelProperty(notes = "Приватность мероприятия, true - закрытое",
            dataType = "Boolean", example = "false", required = true, position = 5)
    private Boolean isPrivate;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + "Цена участия")
    @ApiModelProperty(notes = "Цена участия",
            dataType = "Integer", example = "0", position = 6)
    private Integer price;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + "Максимальное количество участников")
    @ApiModelProperty(notes = "Максимальное количество участников",
            dataType = "Short", example = "10", position = 7)
    private Short maximumUsers;
}
