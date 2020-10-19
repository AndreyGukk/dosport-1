package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Dto представление сущности Мероприятие
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    private Long id;

    private String startDateTime;

    private String stopDateTime;

    private SportTypeDto sportType;

    private FieldDto field;

    private List<UserDto> users;
}
