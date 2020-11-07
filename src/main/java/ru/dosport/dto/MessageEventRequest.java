package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации нового Сообщения
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEventRequest {

    @Size(min = 5, max = 300)
    @NotBlank(message = DATA_NOT_BLANK + "Текст сообщения")
    private String text;
}
