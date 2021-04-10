package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static ru.dosport.helpers.Patterns.*;
import static ru.dosport.helpers.SwaggerMessages.*;


/**
 * Dto представление сущности Площадки.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Представление сущности Площадка для расписания.")
public class SportGroundDto {

    @ApiModelProperty(notes = PAR_SPORTGROUND_ID,
            dataType = "Long", example = "1", required = true, position = 0)
    private Long sportGroundId;

    @Size(max = 100)
    @ApiModelProperty(notes = PAR_SPORTGROUND_CITY,
            dataType = "String", example = "Москва", required = true, position = 1)
    private String city;

    @Size(max = 255)
    @ApiModelProperty(notes = PAR_SPORTGROUND_ADDRESS,
            dataType = "String", example = "ул. Ленинская, 25", required = true, position = 2)
    private String address;

    @Size(max = 150)
    @ApiModelProperty(notes = PAR_SPORTGROUND_NAME,
            dataType = "String", example = "Площадка во дворе дома", required = true, position = 3)
    private String title;

    @ApiModelProperty(notes = PAR_SPORTGROUND_LATITUDE,
            dataType = "Double", required = true, position = 4)
    private Double latitude;

    @ApiModelProperty(notes = PAR_SPORTGROUND_LONGITUDE,
            dataType = "String", required = true, position = 5)
    private Double longitude ;

    @ApiModelProperty(notes = PAR_SPORTGROUND_METRO,
            dataType = "String", position = 6)
    private String metroStation;

    @ApiModelProperty(notes = PAR_SPORTGROUND_SURFACE,
            dataType = "String", position = 7)
    private String surfaceType;

    @ApiModelProperty(notes = PAR_PRICE,
            dataType = "Integer", example = "1000", position = 8)
    private Integer rentPrice;

    @ApiModelProperty(notes = PAR_SPORTGROUND_OPENED,
            dataType = "Boolean", example = "true", position = 9)
    private Boolean opened;

    @ApiModelProperty(notes = PAR_SPORTGROUND_INFRASTRUCTURES,
            dataType = "Set<String>", position = 10)
    private Set<String> infrastructures;

    @ApiModelProperty(notes = PAR_SPORT_TYPE_LIST,
            dataType = "Set<SportTypeDto>",  position = 11)
    private Set<String> sportTypes;

    @ApiModelProperty(notes = PAR_SPORTGROUND_SUBSCRIBERS,
            dataType = "Set<UserDto>",  position = 12)
    private Set<UserDto> subscribers;

    @ApiModelProperty(notes = PAR_SPORTGROUND_REVIEWS,
            dataType = "List<SportGroundReviewDto>",  position = 13)
    private List<SportGroundReviewDto> reviews;

    @ApiModelProperty(notes = PAR_SPORTGROUND_EVENTS,
            dataType = "List<EventDto>",  position = 14)
    private List<EventDto> events;

    @ApiModelProperty(notes = PAR_SPORTGROUND_OPENING_TIME,
            dataType = LOCAL_TIME_TYPE, example = LOCAL_TIME_EXAMPLE_1, position = 15)
    private LocalTime openingTime;

    @ApiModelProperty(notes = PAR_SPORTGROUND_CLOSING_TIME,
            dataType = LOCAL_TIME_TYPE, example = LOCAL_TIME_EXAMPLE_2, position = 16)
    private LocalTime closingTime;
}
