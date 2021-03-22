package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dto представление Информации об ошибке
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление Информации об ошибке")
public class ErrorDto {

    @ApiModelProperty(notes = "Сообщение об ошибке",
            dataType = "String", required = true, position = 0)
    private final String message;
}
