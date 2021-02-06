package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации новой Спортивной площадки
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос для регистрации новой Спортивной площадки.")
public class SportGroundRequest {

    @Size(max = 255)
    @NotNull(message = DATA_NOT_BLANK + "Адрес")
    @ApiModelProperty(notes = "Адрес Площадки", dataType = "String", example = "Ленинградское шоссе, 25", required = true, position = 0)
    private String address;

    @Size(max = 255)
    @NotEmpty(message = DATA_NOT_BLANK + "Название")
    @ApiModelProperty(notes = "Название Площадки", dataType = "String", example = "Ледовый Дворец ЦСКА", required = true, position = 1)
    private String title;

    @Size(max = 255)
    @NotEmpty(message = DATA_NOT_BLANK + "Город")
    @ApiModelProperty(notes = "Город, в котором расположена Площадка", dataType = "String", example = "Москва", required = true, position = 2)
    private String city;

    @NotNull(message = DATA_NOT_BLANK + "Широта")
    @ApiModelProperty(notes = "Широта", dataType = "Double", required = true, position = 3)
    private Double latitude;

    @NotNull(message = DATA_NOT_BLANK + "Долгота")
    @ApiModelProperty(notes = "Долгота", dataType = "Double", required = true, position = 4)
    private Double longitude;

    @ApiModelProperty(notes = "Вид спорта", dataType = "String",  position = 5)
    @NotEmpty(message = DATA_NOT_BLANK + "Вид спорта")
    private List<SportTypeDto> sportTypes;
}
