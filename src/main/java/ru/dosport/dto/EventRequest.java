package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Patterns.*;
import static ru.dosport.helpers.SwaggerMessages.*;

/**
 * Запрос для регистрации нового Мероприятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации нового Мероприятия")
public class EventRequest {

    @NotNull(message = DATA_NOT_BLANK + PAR_START_DATE)
    @FutureOrPresent(message = DATE_MUST_BE_IN_FUTURE + PAR_START_DATE)
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = PAR_START_DATE_FORMAT,
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_1, required = true, position = 0)
    private LocalDateTime startDateTime;

    @NotNull(message = DATA_NOT_BLANK + PAR_END_DATE)
    @FutureOrPresent(message = DATE_MUST_BE_IN_FUTURE + PAR_END_DATE)
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
    @ApiModelProperty(notes = PAR_END_DATE_FORMAT,
            dataType = LOCAL_DATE_TIME_TYPE, example = LOCAL_DATE_TIME_EXAMPLE_2, required = true, position = 1)
    private LocalDateTime endDateTime;

    @NotEmpty(message = DATA_NOT_BLANK + PAR_SPORT_TYPE_NAME)
    @ApiModelProperty(notes = PAR_SPORT_TYPE_NAME,
            dataType = "String", example = "Хоккей", required = true, position = 2)
    private String sportTypeTitle;

    @NotNull(message = DATA_NOT_BLANK + PAR_SPORTGROUND_ID)
    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + PAR_SPORTGROUND_ID)
    @ApiModelProperty(notes = PAR_SPORTGROUND_ID,
            dataType = "Long", example = "1", required = true, position = 3)
    private Long sportGroundId;

    @ApiModelProperty(notes = PAR_EVENT_DESCRIPTION,
            dataType = "String", example = "Классное мероприятие", required = true, position = 4)
    private String description;

    @NotNull(message = DATA_NOT_BLANK + PAR_PRIVATE)
    @ApiModelProperty(notes = PAR_PRIVATE,
            dataType = "Boolean", example = "false", required = true, position = 5)
    private Boolean isPrivate;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + PAR_PRICE)
    @ApiModelProperty(notes = PAR_PRICE,
            dataType = "Integer", example = "0", position = 6)
    private Integer price;

    @PositiveOrZero(message = NUMBER_MUST_BE_POSITIVE + PAR_MAX_USERS)
    @ApiModelProperty(notes = PAR_MAX_USERS,
            dataType = "Short", example = "10", position = 7)
    private Short maximumUsers;
}
