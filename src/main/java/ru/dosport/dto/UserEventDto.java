package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEventDto {

    private String status;

    private Long eventId;

    private LocalDate date;

    private LocalTime startTimeEvent;

    private LocalTime endTimeEvent;

    private String sportType;

    private SportGroundDto sportGroundId;

    private Long chatId;
}
