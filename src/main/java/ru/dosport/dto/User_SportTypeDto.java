package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.dosport.entities.Messages.NOT_BLANK;

/**
 * DTO представление список навыков пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User_SportTypeDto {

    @NotBlank(message = NOT_BLANK)
    private Long user_id;

    private String firstname;

    private String SportType;

    private byte level;

}
