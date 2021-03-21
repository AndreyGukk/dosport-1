package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;


/**
 * Dto представление сущности Площадки.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Представление сущности Площадка для расписания.")
public class SportGroundDto {

    @ApiModelProperty(notes = "Уникальный идентификатор Площадки",
            dataType = "Long", example = "1", required = true, position = 0)
    private Long sportGroundId;

    @Size(max = 100)
    @ApiModelProperty(notes = "Город, в котором расположена Площадка, до 100 символов",
            dataType = "String", example = "Москва", required = true, position = 1)
    private String city;

    @Size(max = 255)
    @ApiModelProperty(notes = "Адрес Площадки, до 255 символов",
            dataType = "String", example = "ул. Ленинская, 25", required = true, position = 2)
    private String address;

    @Size(max = 150)
    @ApiModelProperty(notes = "Название Площадки, до 150 символов",
            dataType = "String", example = "Площадка во дворе дома", required = true, position = 3)
    private String title;

    @ApiModelProperty(notes = "Широта",
            dataType = "Double", required = true, position = 4)
    private Double latitude;

    @ApiModelProperty(notes = "Долгота",
            dataType = "String", required = true, position = 5)
    private Double longitude ;

    @ApiModelProperty(notes = "Станция метро, варианты: Не выбрано",
            dataType = "String", position = 6)
    private String metroStation;

    @ApiModelProperty(notes = "Тип покрытия, варианты: Не выбрано, Грунт, Песок, Асфальт, Резина, " +
            "Искуственный газон, Натуральный газон, Паркет, Лед, Хард, Маты, Бассейн",
            dataType = "String", position = 7)
    private String surfaceType;

    @ApiModelProperty(notes = "Стоимость аренды в час",
            dataType = "Integer", example = "1000", position = 8)
    private Integer rentPrice;

    @ApiModelProperty(notes = "Является ли площадка открытой (расположена на улице)",
            dataType = "boolean", example = "true", required = true, position = 9)
    private Boolean opened;

    @ApiModelProperty(notes = "Список инфраструктуры площадки, варианты: Не выбрано, " +
            "Раздевалка, Парковка, Трибуны, Душ, Освещение, Камера хранения, Аренда оборудования",
            dataType = "List<String>", position = 10)
    private List<String> infrastructures;

    @ApiModelProperty(notes = "Список видов спорта",
            dataType = "List<SportTypeDto>",  position = 11)
    private List<SportTypeDto> sportTypes;
}
