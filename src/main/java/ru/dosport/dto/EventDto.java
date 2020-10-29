package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.dosport.entities.EventMember;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Dto представление сущности Мероприятие
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    private Long eventId;

    private LocalDate dateEvent;

    private LocalDateTime startTimeEvent;

    private LocalDateTime endTimeEvent;

    private SportTypeDto sportType;

    private Long sportGroundId;

    private Long organizerId;

    private Set<MemberDto> members;

    private Long chatId;
}
