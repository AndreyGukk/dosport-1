package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Dto представление сущности Мероприятие
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    private Long idEvent;

    private LocalDate dateEvent;

    private String startTimeEvent;

    private String endTimeEvent;

    private SportTypeDto sportType;

    private Long idSportGround;

    private List<UserDto> users;

    private Long idChat;
}
