package ru.dosport.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Ошибка: Запрос не удалось обработать из-за синтаксической ошибки.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EntityBadRequestException extends RuntimeException {

    public EntityBadRequestException(String message) {
        super(message);
    }
}
