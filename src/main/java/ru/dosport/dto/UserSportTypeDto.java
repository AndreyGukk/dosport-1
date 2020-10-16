package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;


/**
 * DTO представление список навыков пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSportTypeDto {
    @Digits(integer = 20, fraction = 0)
    private Long user_id;

    private String firstname;

    private String SportType;

    private byte level;

}
