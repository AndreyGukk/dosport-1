package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_BLANK;
import static ru.dosport.helpers.InformationMessages.NUMBER_MUST_BE_POSITIVE;
import static ru.dosport.helpers.Patterns.*;

/**
 * Dto представление сущности Мероприятие
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Мероприятие")
public class EventDto {

    @ApiModelProperty(notes = "Уникальный идентификатор Мероприятия",
            dataType = "Long", example = "1", required = true, position = 0)
    private Long eventId;

    @NotNull(message = DATA_NOT_BLANK + "Дата создания")
    @FutureOrPresent
    @DateTimeFormat(pattern=LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = "Дата и время создания Мероприятия",
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_EXAMPLE_1, required = true, position = 0)
    private String creationDateTime;

    @NotNull(message = DATA_NOT_BLANK + "Дата и время начала")
    @FutureOrPresent
    @DateTimeFormat(pattern=LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = "Дата и время начала проведения Мероприятия",
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_1, required = true, position = 1)
    private String startDateTime;

    @NotNull(message = DATA_NOT_BLANK + "Дата и время начала")
    @FutureOrPresent
    @DateTimeFormat(pattern=LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = "Дата и время окончания проведения Мероприятия",
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_2, required = true, position = 2)
    private String endDateTime;

    @ApiModelProperty(notes = "Вид спорта",
            dataType = "SportTypeDto", required = true, position = 3)
    private SportTypeDto sportType;

    @NotNull(message = DATA_NOT_BLANK + "Спортивная площадки")
    @ApiModelProperty(notes = "Идентификатор спортивной площадки для проведения Мероприятия",
            dataType = "Long", example = "1", required = true, position = 4)
    private Long sportGroundId;

    @ApiModelProperty(notes = "Организатор Мероприятия",
            dataType = "UserDto", required = true, position = 5)
    private UserDto organizer;

    @ApiModelProperty(notes = "Список участников Мероприятия",
            dataType = "Set<UserDto>", required = true, position = 6)
    private Set<UserDto> participants;

    @ApiModelProperty(notes = "Описание мероприятияб до 150 символов",
            dataType = "String", example = "Классное мероприятие", required = true, position = 7)
    private String description;

    @NotNull(message = DATA_NOT_BLANK + "Приватность мероприятия")
    @ApiModelProperty(notes = "Приватность мероприятия, true - закрытое",
            dataType = "Boolean", example = "true", required = true, position = 8)
    private Boolean isPrivate;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + "Цена участия в мероприятии")
    @ApiModelProperty(notes = "Цена участия в мероприятии",
            dataType = "Integer", example = "100", position = 9)
    private Integer price;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + "Максимальное количество участников")
    @ApiModelProperty(notes = "Максимальное количество участников",
            dataType = "Short", example = "10", position = 10)
    private Short maximumUsers;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + "Количество участников")
    @ApiModelProperty(notes = "Количество участников",
            dataType = "Short", example = "1", position = 11)
    private Short usersAmount;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + "Количество сообщений")
    @ApiModelProperty(notes = "Количество сообщений",
            dataType = "Short", example = "1", position = 12)
    private Short messagesAmount;
}
