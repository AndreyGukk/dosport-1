package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentSportGroundDto {

    private Long commentId;

    private Long userId;

    private String userName;

    private Long sportGroundId;

    private LocalDate date;

    private String text;
}
