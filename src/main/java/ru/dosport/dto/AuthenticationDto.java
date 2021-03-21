package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dto представление Данных авторизации
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Dto представление Данных авторизации")
public class AuthenticationDto {

    @ApiModelProperty(notes = "Токен авторизации",
            dataType = "String", example = "Bearer_1lzfk5qn6dqg8ulk8dvx1nksz", required = true, position = 0)
    private final String token;
}
