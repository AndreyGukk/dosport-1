package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEventDto {

    private Long id;

    private Long eventId;

    private Long userId;

    private String userName;

    private String text;
}
