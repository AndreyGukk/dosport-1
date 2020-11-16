package ru.dosport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import static ru.dosport.helpers.Messages.BAD_REQUEST;
import static ru.dosport.helpers.Messages.USER_NOT_FOUND_BY_ID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSportGroundDto {

    @NotBlank
    @Digits(integer = 19, fraction = 0, message = USER_NOT_FOUND_BY_ID)
    private long userId;

    @NotBlank
    @Digits(integer = 19, fraction = 0, message = BAD_REQUEST)
    private long sportGroundId;

}
