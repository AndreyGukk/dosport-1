package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * Dto представление сущности Мероприятие
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    private Long eventId;

    private LocalDate dateEvent;

    private LocalTime startTimeEvent;

    private LocalTime endTimeEvent;

    private SportTypeDto sportType;

    private Long sportGroundId;

    private Long organizerId;

    private Set<MemberDto> members;

    private Long chatId;
}
