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

    @Size(max = 100)
    @NotEmpty(message = DATA_NOT_BLANK + "Город")
    @ApiModelProperty(notes = "Город, в котором расположена Площадка, до 100 символов",
            dataType = "String", example = "Москва", required = true, position = 0)
    private String city;

    @Size(max = 255)
    @NotNull(message = DATA_NOT_BLANK + "Адрес")
    @ApiModelProperty(notes = "Адрес Площадки, до 255 символов",
            dataType = "String", example = "Ленинградское шоссе, 25", required = true, position = 1)
    private String address;

    @Size(max = 150)
    @NotEmpty(message = DATA_NOT_BLANK + "Название")
    @ApiModelProperty(notes = "Название Площадки, до 255 символов",
            dataType = "String", example = "Ледовый Дворец ЦСКА", required = true, position = 2)
    private String title;

    @NotNull(message = DATA_NOT_BLANK + "Широта")
    @ApiModelProperty(notes = "Широта",
            dataType = "Double", required = true, position = 3)
    private Double latitude;

    @NotNull(message = DATA_NOT_BLANK + "Долгота")
    @ApiModelProperty(notes = "Долгота",
            dataType = "Double", required = true, position = 4)
    private Double longitude;

    @ApiModelProperty(notes = "Станция метро, варианты: Не выбрано",
            dataType = "String", position = 5)
    private String metroStation;

    @ApiModelProperty(notes = "Тип покрытия, варианты: Не выбрано, Грунт, Песок, Асфальт, Резина, " +
            "Искуственный газон, Натуральный газон, Паркет, Лед, Хард, Маты, Бассейн",
            dataType = "String", position = 6)
    private String surfaceType;

    @ApiModelProperty(notes = "Стоимость аренды в час",
            dataType = "Integer", example = "1000", position = 7)
    private Integer rentPrice;

    @ApiModelProperty(notes = "Является ли площадка открытой (расположена на улице)",
            dataType = "boolean", example = "true", required = true, position = 8)
    private Boolean opened;

    @ApiModelProperty(notes = "Список инфраструктуры площадки, варианты: Не выбрано, " +
            "Раздевалка, Парковка, Трибуны, Душ, Освещение, Камера хранения, Аренда оборудования",
            dataType = "List<String>", position = 9)
    private List<String> infrastructures;

    @ApiModelProperty(notes = "Список видов спорта",
            dataType = "List<SportTypeDto>", position = 10)
    private List<SportTypeDto> sportTypes;
}
