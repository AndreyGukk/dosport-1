package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации новой Спортивной площадки
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportGroundRequest {

    @NotBlank(message = DATA_NOT_BLANK + "Адрес")
    private String address;

    @NotBlank(message = DATA_NOT_BLANK + "Название")
    private String title;

    @NotBlank(message = DATA_NOT_BLANK + "Вид спорта")
    private List<SportTypeDto> sportTypes;
}
