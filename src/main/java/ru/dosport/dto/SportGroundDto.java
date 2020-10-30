package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

/**
 * Dto представление сущности Площадки для расписания.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportGroundDto {

    private Long sportGroundId;

    private String address;

    private String title;

    private List<SportTypeDto> sportTypes;

    private List<EventDto> events;

    private List<CommentSportGroundDto> commentSportGrounds;
}
