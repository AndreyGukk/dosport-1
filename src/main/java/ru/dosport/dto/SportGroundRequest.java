package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации новой Спортивной площадки
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportGroundRequest {

    @NotNull(message = DATA_NOT_BLANK + "Адрес")
    private String address;

    @NotEmpty(message = DATA_NOT_BLANK + "Название")
    private String title;

    @NotEmpty(message = DATA_NOT_BLANK + "Город")
    private String city;

    @NotNull(message = DATA_NOT_BLANK + "Широта")
    private Double latitude;

    @NotNull(message = DATA_NOT_BLANK + "Долгота")
    private Double longitude;

    @NotEmpty(message = DATA_NOT_BLANK + "Вид спорта")
    private List<SportTypeDto> sportTypes;
}
