package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.geo.Point;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Dto представление сущности Площадки для расписания.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportGroundDto {

    private Long sportGroundId;

    private String address;

    private String city;

    private String title;

    private Double latitude;

    private Double longitude ;

    private List<SportTypeDto> sportTypes;

    private List<EventDto> events;

    private List<CommentSportGroundDto> commentSportGrounds;
}
