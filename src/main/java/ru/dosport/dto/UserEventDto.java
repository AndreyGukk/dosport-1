package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEventDto {
   // private UserDto user;

    private String statusUser;

    private Long eventId;

    private LocalDate dateEvent;

    private LocalTime startTimeEvent;

    private LocalTime endTimeEvent;

    private SportTypeDto sportType;

    private Long sportGroundId;

    private Long chatId;
}
