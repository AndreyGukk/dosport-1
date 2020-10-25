package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDto {

    private Long idEvent;
    private UserDto user;
    private String statusUser;
}
