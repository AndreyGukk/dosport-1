package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.dosport.entities.CommentSportGround;
import ru.dosport.entities.Event;
import ru.dosport.entities.SportType;

import javax.persistence.*;
import java.util.List;

/**
 * Dto представление сущности Площадки для расписания.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportGroundDto {
    //todo    Карта указыватеся есть ивент или нет на площадке;

    private Long idSportGround;

    private String address;

    private String title;

    private List<SportTypeDto> sportTypes;

    private List<EventDto> events;

    private List<CommentSportGroundDto> commentSportGrounds;
}
