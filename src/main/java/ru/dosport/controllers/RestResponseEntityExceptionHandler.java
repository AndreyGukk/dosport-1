package ru.dosport.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.dosport.dto.ErrorDto;
import ru.dosport.exceptions.EntityBadRequestException;
import ru.dosport.exceptions.EntityNotFoundException;

import static ru.dosport.entities.Messages.ACCESS_DENIED;

/**
 * Контроллер - глобальный обработчик исключений.
 */
@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception ex, WebRequest request) {
        log.error(ACCESS_DENIED);
        return new ResponseEntity<>(new ErrorDto(400, ACCESS_DENIED), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorDto> handleUsernameNotFoundException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(401, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(Exception ex, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(404, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ EntityBadRequestException.class })
    public ResponseEntity<ErrorDto> handleEntityBadRequestException(Exception ex, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
