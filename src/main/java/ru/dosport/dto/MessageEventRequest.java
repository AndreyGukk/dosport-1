package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Запрос для регистрации новой Спортивной площадки
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEventRequest {

    private String userName;

    private String text;
}
