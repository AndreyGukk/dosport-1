package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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

    @DateTimeFormat(pattern="yyyy.MM.dd")
    @ApiModelProperty(notes = "Дата Мероприятия",
            dataType = "LocalDate", example = "2007-12-03", required = true, position = 1)
    private LocalDate dateEvent;

    @DateTimeFormat(pattern="HH:mm")
    @ApiModelProperty(notes = "Время начала Мероприятия",
            dataType = "LocalTime", example = "10:15", required = true, position = 2)
    private LocalTime startTimeEvent;

    @DateTimeFormat(pattern="HH:mm")
    @ApiModelProperty(notes = "Время окончания Мероприятия",
            dataType = "LocalTime", example = "10:15", required = true, position = 3)
    private LocalTime endTimeEvent;

    @ApiModelProperty(notes = "Вид спорта",
            dataType = "SportTypeDto",  required = true, position = 4)
    private SportTypeDto sportType;

    @ApiModelProperty(notes = "Идентификатор спортивной площадки для проведения Мероприятия",
            dataType = "Long", example = "1", required = true, position = 5)
    private Long sportGroundId;

    @ApiModelProperty(notes = "Идентификатор организатора Мероприятия",
            dataType = "Long", example = "1", required = true, position = 6)
    private Long organizerId;

    @ApiModelProperty(notes = "Список участников Мероприятия",
            dataType = "Set<MemberDto>", required = true, position = 7)
    private Set<MemberDto> members;

    @ApiModelProperty(notes = "Идентификатор чата мероприятия",
            dataType = "Long", example = "1", required = true, position = 8)
    private Long chatId;
}
