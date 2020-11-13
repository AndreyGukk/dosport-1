package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Dto представление Информации об ошибке
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDto {

    private final String message;
}
