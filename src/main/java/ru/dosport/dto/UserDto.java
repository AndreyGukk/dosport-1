package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_BLANK;
import static ru.dosport.helpers.InformationMessages.INVALID_USERNAME_LENGTH;
import static ru.dosport.helpers.Patterns.*;

/**
 * Dto представление сущности Пользователь
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Пользователь")
public class UserDto {

    @NotBlank(message = DATA_NOT_BLANK)
    @Positive
    @ApiModelProperty(notes = "Уникальный идентификатор пользователя, больше 0",
            dataType = "Long", example = "1", required = true, position = 0)
    private Long id;

    @Size(min = 4, max = 50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Никнейм")
    @ApiModelProperty(notes = "Никнейм, от 4 до 50 символов",
            dataType = "String", example = "Nickname", required = true, position = 1)
    private String username;

    @ApiModelProperty(notes = "Дата рождения пользователя в формате " + LOCAL_DATE_PATTERN,
            dataType = LOCAL_DATE_TYPE, example = LOCAL_DATE_EXAMPLE_1, position = 2)
    private String birthdayDate;

    @NotBlank(message = DATA_NOT_BLANK + "Пол пользователя")
    @ApiModelProperty(notes = "Пол пользователя, значения: 'Женский', 'Мужской'",
            dataType = "String", example = "Мужской", position = 3)
    private String gender;

    @ApiModelProperty(notes = "Личная информация о пользователе, до 150 символов",
            dataType = "String", example = "Информация о пользователе", position = 4)
    @Size(max = 150)
    private String info;

    @ApiModelProperty(notes = "Ссылка на файл фото, до 150 символов",
            dataType = "String", example = "myphoto.png", position = 5)
    @Size(max = 150)
    private String photoLink;
}
