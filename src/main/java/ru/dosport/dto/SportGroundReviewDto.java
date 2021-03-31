package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.dosport.entities.User;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_BLANK;
import static ru.dosport.helpers.Patterns.*;

/**
 * Dto представление сущности Отзыв о площадке
 */
@Data
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление сущности Отзыв о площадке")
public class SportGroundReviewDto {

    @NotNull(message = DATA_NOT_BLANK + "Индентификатор отзыва")
    @ApiModelProperty(notes = "Идентификатор отзыва",
            dataType = "Long", example = "1", required = true, position = 0)
    private Long reviewId;

//    @NotNull(message = DATA_NOT_BLANK + "Индентификатор пользователя")
//    @ApiModelProperty(notes = "Уникальный идентификатор пользователя",
//            dataType = "Long", example = "1", required = true, position = 1)
//    private User user;

    @Size(min = 2, max = 50)
    @NotBlank(message = DATA_NOT_BLANK + "Полное имя пользователя")
    @ApiModelProperty(notes = "Имя пользователя",
            dataType = "String", required = true, position = 2)
    private String username;

    @NotNull(message = DATA_NOT_BLANK + "Индентификатор площадки")
    @ApiModelProperty(notes = "Идентификатор спортивной площадки",
            dataType = "Long", example = "1", required = true, position = 3)
    private Long sportGroundId;

    @FutureOrPresent
    @NotBlank(message = DATA_NOT_BLANK + "Дата")
    @DateTimeFormat(pattern = LOCAL_DATE_PATTERN)
    @ApiModelProperty(notes = "Дата проведения Мероприятия",
            dataType = LOCAL_DATE_TYPE, example = LOCAL_DATE_EXAMPLE_1, required = true, position = 4)
    private LocalDate date;

    @Size(min = 5, max = 300)
    @NotBlank(message = DATA_NOT_BLANK + "Текст отзыва")
    @ApiModelProperty(notes = "Текст отзыва",
            dataType = "String", required = true, position = 5)
    private String text;
}
