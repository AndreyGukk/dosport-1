package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ReviewRequest {

    @Size(min = 5, max = 300)
    @NotBlank(message = DATA_NOT_BLANK + "Текст отзыва")
    private String text;
}
