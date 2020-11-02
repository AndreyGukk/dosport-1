package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

import static ru.dosport.helpers.Messages.USER_NOT_FOUND_BY_ID;

/**
 * Dto представление сущности Спортивные навыки пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSportTypeDto {

    @Positive
    @Digits(integer = 19, fraction = 0, message = USER_NOT_FOUND_BY_ID)
    private Long userId;

    private String firstname;

    private String SportType;

    private short level;
}
