package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Dto представление сущности Вид спорта
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Вид спорта")
public class SportTypeDto {

    @ApiModelProperty(notes = "Уникальный идентификатор вида спорта",
            dataType = "Long", example = "1", required = true, position = 0)
    private Short sportTypeId;

    @Size(max = 255)
    @NotBlank(message = DATA_NOT_BLANK + "Название")
    @ApiModelProperty(notes = "Название вида спорта, до 255 символов",
            dataType = "String", example = "Бокс",  required = true, position = 1)
    private String title;
}
