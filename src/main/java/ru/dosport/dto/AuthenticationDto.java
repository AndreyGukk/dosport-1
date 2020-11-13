package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Dto представление Данных авторизации
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationDto {

    private final String token;
}
