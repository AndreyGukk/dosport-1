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
import java.util.Set;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

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

    @FutureOrPresent
    @NotNull(message = DATA_NOT_BLANK + "Дата создания")
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
    @ApiModelProperty(notes = "Дата и время создания Мероприятия",
            dataType = "LocalDateTime", example = "2020-12-03 10:15", required = true, position = 0)
    private LocalDateTime creationDateTime;

    @NotNull(message = DATA_NOT_BLANK + "Дата и время начала")
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
    @ApiModelProperty(notes = "Дата и время начала проведения Мероприятия",
            dataType = "LocalDateTime", example = "2020-12-03 10:15", required = true, position = 1)
    private LocalDateTime startDateTime;

    @NotNull(message = DATA_NOT_BLANK + "Дата и время начала")
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm")
    @ApiModelProperty(notes = "Дата и время окончания проведения Мероприятия",
            dataType = "LocalDateTime", example = "2020-12-03 10:15", required = true, position = 2)
    private LocalDateTime endDateTime;

    @ApiModelProperty(notes = "Вид спорта",
            dataType = "SportTypeDto",  required = true, position = 3)
    private SportTypeDto sportType;

    @ApiModelProperty(notes = "Идентификатор спортивной площадки для проведения Мероприятия",
            dataType = "Long", example = "1", required = true, position = 4)
    private Long sportGroundId;

    @ApiModelProperty(notes = "Идентификатор организатора Мероприятия",
            dataType = "Long", example = "1", required = true, position = 5)
    private Long organizerId;

    @ApiModelProperty(notes = "Список участников Мероприятия",
            dataType = "Set<MemberDto>", required = true, position = 6)
    private Set<MemberDto> members;

    @ApiModelProperty(notes = "Описание мероприятияб до 150 символов",
            dataType = "String", example = "Классное мероприятие", required = true, position = 7)
    private String description;

    @NotEmpty(message = DATA_NOT_BLANK + "Приватность мероприятия")
    @ApiModelProperty(notes = "Приватность мероприятия, true - закрытое",
            dataType = "Boolean", example = "true", required = true, position = 8)
    private Boolean isPrivate;

    @PositiveOrZero
    @ApiModelProperty(notes = "Цена участия в мероприятии",
            dataType = "Integer", example = "100", position = 9)
    private Integer price;

    @PositiveOrZero
    @ApiModelProperty(notes = "Максимальное количество участников мероприятия",
            dataType = "Short", example = "10", position = 10)
    private Short maximumMembers;
}
