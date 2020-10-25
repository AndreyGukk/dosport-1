package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

/**
 * Dto представление сущности Спортивные навыки пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSportTypeDto {

@Positive
@Digits(integer = 19, fraction = 0)
    private Long userId;

    private String firstname;

    private String SportType;

    private byte level;
}
