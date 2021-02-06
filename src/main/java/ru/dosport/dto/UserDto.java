package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;
import static ru.dosport.helpers.Messages.INVALID_USERNAME_LENGTH;

/**
 * Dto представление сущности Пользователь
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Пользователь")
public class UserDto {

    @NotBlank(message = DATA_NOT_BLANK)
    @ApiModelProperty(notes = "Уникальный идентификатор пользователя", dataType = "Long", example = "1", required = true, position = 0)
    private Long id;

    @Email
    @Size(min=4, max=50, message = INVALID_USERNAME_LENGTH)
    @NotBlank(message = DATA_NOT_BLANK + "Адрес эл. почты")
    @ApiModelProperty(notes = "Emeil пользователя!", dataType = "String", example = "abc@mail.ru", required = true, position = 1)
    private String username;

    @ApiModelProperty(notes = "Дата рожнения пользователя", dataType = "String", example = "2003-04-05",  position = 2)
    private String birthdayDate;

    @ApiModelProperty(notes = "Скрыть/показать дату рождения пользователя", dataType = "Boolean",  example = "true", position = 3)
    @Size(min=1, max=128, message = INVALID_USERNAME_LENGTH)
    @NotNull(message = DATA_NOT_BLANK + "Скрыть дату рождения")
    private boolean hideBirthdayDate;

    @ApiModelProperty(notes = "Имя пользователя", dataType = "String",  example = "Иван", required = true, position = 4)
    @NotBlank(message = DATA_NOT_BLANK + "Имя")
    private String firstName;

    @ApiModelProperty(notes = "Фамилия пользователя", dataType = "String",  example = "Иванов",  required = true, position = 5)
    private String lastName;

    @ApiModelProperty(notes = "Пол пользователя", dataType = "String",  example = "2", required = true, position = 6)
    @NotBlank(message = DATA_NOT_BLANK + "Пол")
    private String gender;

    @ApiModelProperty(notes = "Информация о пользователе", dataType = "String",  example = "Информация о пользователе", position = 7)
    @Size(max=1024)
    private String info;

    @ApiModelProperty(notes = "Ссылка на файл фото", dataType = "String",  example = "myphoto.png", position = 8)
    @Size(max = 1024)
    private String photoLink;
}
