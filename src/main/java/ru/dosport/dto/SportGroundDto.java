package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;


/**
 * Dto представление сущности Площадки для расписания.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Представление сущности Площадка для расписания.")
public class SportGroundDto {

    @ApiModelProperty(notes = "Уникальный идентификатор Площадки", dataType = "Long", example = "1", required = true, position = 0)
    private Long sportGroundId;

    @Size(max = 255)
    @ApiModelProperty(notes = "Адрес Площадки", dataType = "String", example = "Ленинский, 25", required = true, position = 1)
    private String address;

    @Size(max = 255)
    @ApiModelProperty(notes = "Город, в котором расположена Площадка", dataType = "String", example = "Ленинский, 25", required = true, position = 2)
    private String city;

    @Size(max = 255)
    @ApiModelProperty(notes = "Название Площадки", dataType = "String", example = "Ленинский, 25", required = true, position = 3)
    private String title;

    @ApiModelProperty(notes = "Широта", dataType = "Double", required = true, position = 4)
    private Double latitude;

    @ApiModelProperty(notes = "Долгота", dataType = "Double", required = true, position = 5)
    private Double longitude ;

    @ApiModelProperty(notes = "Лист видов спорта", dataType = "List<SportTypeDto>",  position = 6)
    private List<SportTypeDto> sportTypes;

    @ApiModelProperty(notes = "Лист видов спорта", dataType = "List<EventDto>", position = 7)
    private List<EventDto> events;
}
