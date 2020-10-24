package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentSportGroundDto {

    private Long idComment;

    private Long idUser;

    private String userName;

    private Long idSportGround;

    private LocalDate date;

    private String text;
}
