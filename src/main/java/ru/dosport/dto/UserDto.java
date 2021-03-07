package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

import static ru.dosport.helpers.Messages.*;

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

    @Email
    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Адрес эл. почты")
    @ApiModelProperty(notes = "Адрес эл. почты, от 4 до 50 символов",
            dataType = "String", example = "abc@mail.ru", required = true, position = 1)
    private String username;

    @ApiModelProperty(notes = "Дата рождения пользователя в формате dd-MM-yyyy",
            dataType = "String", example = "31-01-2001",  position = 2)
    private String birthdayDate;

    @NotNull(message = DATA_NOT_BLANK + "Скрыть дату рождения")
    @ApiModelProperty(notes = "Значение флага сокрытия даты рождения пользователя",
            dataType = "Boolean",  example = "true", position = 3)
    private boolean hideBirthdayDate;

    @Size(min=2, max=50, message = INVALID_FIRSTNAME_LENGTH)
    @ApiModelProperty(notes = "Имя пользователя, от 2 до 50 символов",
            dataType = "String", example = "Иван", required = true, position = 4)
    private String firstName;

    @Size(min=2, max=100, message = INVALID_LASTNAME_LENGTH)
    @ApiModelProperty(notes = "Фамилия пользователя, от 2 до 100 символов",
            dataType = "String", example = "Иванов",  required = false, position = 5)
    private String lastName;

    @NotBlank(message = DATA_NOT_BLANK + "Пол пользователя")
    @ApiModelProperty(notes = "Пол пользователя, значения: 'Не выбран' или 0, 'Женский' или 1, 'Мужской' или 2",
            dataType = "String", example = "2", required = true, position = 6)
    private String gender;

    @ApiModelProperty(notes = "Личная информация о пользователе, до 250 символов",
            dataType = "String", example = "Информация о пользователе", position = 7)
    @Size(max=250)
    private String info;

    @ApiModelProperty(notes = "Ссылка на файл фото, до 250 символов",
            dataType = "String", example = "myphoto.png", position = 8)
    @Size(max = 250)
    private String photoLink;
}
