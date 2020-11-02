package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Dto представление сущности Участник меропрятия
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDto {

    private Long eventId;

    private UserDto user;

    private String statusUser;
}
