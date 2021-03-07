package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

import static ru.dosport.helpers.Messages.USER_NOT_FOUND_BY_ID;

/**
 * Dto представление сущности Спортивные навыки пользователя
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Спортивные навыки пользователя")
public class UserSportTypeDto {

    @Positive
    @Digits(integer = 19, fraction = 0, message = USER_NOT_FOUND_BY_ID)
    @ApiModelProperty(notes = "Уникальный идентификатор пользователя",
            dataType = "Long", example = "1", required = true, position = 0)
    private Long userId;

    @ApiModelProperty(notes = "Имя пользователя",
            dataType = "String",  example = "Иван", required = true, position = 1)
    private String firstname;

    @ApiModelProperty(notes = "Название вида спорта",
            dataType = "String", example = "Бокс",  position = 2)
    private String SportType;

    @ApiModelProperty(notes = "Классификатор уровня пользователя",
            dataType = "Short", example = "1", required = true, position = 3)
    private short level;
}
