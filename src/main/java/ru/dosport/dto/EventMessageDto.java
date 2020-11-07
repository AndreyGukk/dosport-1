package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventMessageDto {

    private Long id;

    private Long eventId;

    private Long userId;

    private String userName;

    @Size(min = 5, max = 300)
    @NotBlank(message = DATA_NOT_BLANK + "Текст сообщения")
    private String text;
}
