package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;

import static ru.dosport.helpers.Patterns.*;
import static ru.dosport.helpers.SwaggerMessages.*;

/**
 * Dto представление сущности Мероприятие
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Мероприятие")
public class EventDto {

    @ApiModelProperty(notes = PAR_EVENT_ID,
            dataType = "Long", example = "1", required = true, position = 0)
    private Long eventId;

    @DateTimeFormat(pattern=LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = PAR_CREATION_DATE_FORMAT,
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_EXAMPLE_1, required = true, position = 0)
    private String creationDateTime;

    @DateTimeFormat(pattern=LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = PAR_START_DATE_FORMAT,
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_1, required = true, position = 1)
    private String startDateTime;

    @DateTimeFormat(pattern=LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = PAR_END_DATE_FORMAT,
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_2, required = true, position = 2)
    private String endDateTime;

    @ApiModelProperty(notes = PAR_SPORT_TYPE_DTO,
            dataType = "SportTypeDto", required = true, position = 3)
    private SportTypeDto sportType;

    @ApiModelProperty(notes = PAR_SPORTGROUND_ID,
            dataType = "Long", example = "1", required = true, position = 4)
    private Long sportGroundId;

    @ApiModelProperty(notes = PAR_ORGANIZER_DTO,
            dataType = "UserDto", required = true, position = 5)
    private UserDto organizer;

    @ApiModelProperty(notes = PAR_PARTICIPANTS,
            dataType = "Set<UserDto>", required = true, position = 6)
    private Set<UserDto> participants;

    @ApiModelProperty(notes = PAR_EVENT_DESCRIPTION,
            dataType = "String", example = "Классное мероприятие", required = true, position = 7)
    private String description;

    @ApiModelProperty(notes = PAR_PRIVATE,
            dataType = "Boolean", example = "true", required = true, position = 8)
    private Boolean isPrivate;

    @ApiModelProperty(notes = PAR_PRICE,
            dataType = "Integer", example = "100", position = 9)
    private Integer price;

    @ApiModelProperty(notes = PAR_MAX_USERS,
            dataType = "Short", example = "10", position = 10)
    private Short maximumUsers;

    @ApiModelProperty(notes = PAR_USERS_COUNT,
            dataType = "Short", example = "1", position = 11)
    private Short usersAmount;

    @ApiModelProperty(notes = PAR_MESSAGES_COUNT,
            dataType = "Short", example = "1", position = 12)
    private Short messagesAmount;
}
