package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Dto представление сущности Отзыв о площадке
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentSportGroundDto {

    @NotNull(message = DATA_NOT_BLANK + "Индентификатор отзыва")
    private Long commentId;

    @NotNull(message = DATA_NOT_BLANK + "Индентификатор пользователя")
    private Long userId;

    @Size(min = 2, max = 50)
    @NotBlank(message = DATA_NOT_BLANK + "Полное имя пользователя")
    private String userFullName;

    @NotNull(message = DATA_NOT_BLANK + "Индентификатор площадки")
    private Long sportGroundId;

    @FutureOrPresent
    @NotBlank(message = DATA_NOT_BLANK + "Дата")
    private LocalDate date;

    @Size(min = 5, max = 300)
    @NotBlank(message = DATA_NOT_BLANK + "Текст отзова")
    private String text;
}
