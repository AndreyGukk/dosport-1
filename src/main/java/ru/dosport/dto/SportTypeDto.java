package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static ru.dosport.helpers.Messages.DATA_NOT_BLANK;

/**
 * Сущность вида спорта
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportTypeDto {
    private short id;

    @NotBlank(message = DATA_NOT_BLANK + "вид спорта")
    private String title;
}
