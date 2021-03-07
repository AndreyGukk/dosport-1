package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос на создание сущности Отзывв о спортивной площадке
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Запрос на создание сущности Отзывв о спортивной площадке")
public class ReviewRequest {

    @Size(min = 5, max = 300)
    @NotBlank(message = DATA_NOT_BLANK + "Текст отзыва")
    @ApiModelProperty(notes = "Текст отзыва", dataType = "String", required = true, position = 0)
    private String text;
}
