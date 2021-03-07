package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto для предоставления пользователю данных о мероприятии")
public class UserEventDto {

    @ApiModelProperty(notes = "Статус пользователя как участника события",
            dataType = "String", position = 0)
    private String status;

    @ApiModelProperty(notes = "Уникальный идентификатор события",
            dataType = "Long", example = "1", required = true, position = 1)
    private Long eventId;

    @DateTimeFormat(pattern="yyyy.MM.dd")
    @ApiModelProperty(notes = "Дата проведения Мероприятия",
            dataType = "LocalDate", example = "2007-12-03", required = true, position = 2)
    private LocalDate date;

    @DateTimeFormat(pattern="HH:mm")
    @ApiModelProperty(notes = "Время начала проведения Мероприятия",
            dataType = "LocalTime", example = "10:15", required = true, position = 3)
    private LocalTime startTimeEvent;

    @DateTimeFormat(pattern="HH:mm")
    @ApiModelProperty(notes = "Время окончания проведения Мероприятия",
            dataType = "LocalTime", example = "10:15", required = true, position = 4)
    private LocalTime endTimeEvent;

    @ApiModelProperty(notes = "Название вида спорта",
            dataType = "String", example = "Хоккей", required = true, position = 5)
    private String sportType;

    @ApiModelProperty(notes = "Название Площадки",
            dataType = "String", example = "Ледовый Дворец ЦСКА", required = true, position = 6)
    private SportGroundDto sportGroundId;

    @ApiModelProperty(notes = "Уникальный идентификатор чата",
            dataType = "Long", example = "1", required = true, position = 7)
    private Long chatId;
}
