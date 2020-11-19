package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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

    @NotBlank(message = DATA_NOT_BLANK + "Адрес")
    private String address;

    @NotBlank(message = DATA_NOT_BLANK + "Город")
    private String city;

    @NotBlank(message = DATA_NOT_BLANK + "Название")
    private String title;

    @NotBlank(message = DATA_NOT_BLANK + "Широта")
    private Double latitude;

    @NotBlank(message = DATA_NOT_BLANK + "Долгота")
    private Double longitude ;

    @NotBlank(message = DATA_NOT_BLANK + "Вид спорта")
    private List<SportTypeDto> sportTypes;

    private List<EventDto> events;
}
