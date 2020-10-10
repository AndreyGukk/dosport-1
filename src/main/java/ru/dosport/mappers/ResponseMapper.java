package ru.dosport.mappers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.dosport.dto.*;

import java.util.List;

/**
 * Класс для маппинга Dto объектов в Response. Формирует необходимый ответ в зависимости от содержания objectDto.
 */
public final class ResponseMapper {

    public static ResponseEntity<Boolean> getBooleanResponse(Boolean objectDto) {
        return objectDto == null ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(objectDto, HttpStatus.OK);
    }

    public static ResponseEntity<UserDto> getDtoResponse(UserDto objectDto) {
        return objectDto == null ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(objectDto, HttpStatus.OK);
    }

    public static ResponseEntity<List<UserDto>> getListUserDtoResponse(List<UserDto> objectDtoList) {
        return objectDtoList.size() == 0 ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(objectDtoList, HttpStatus.OK);
    }
}
