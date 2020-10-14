package ru.dosport.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.dosport.dto.ErrorDto;
import ru.dosport.exceptions.EntityBadRequestException;
import ru.dosport.exceptions.EntityNotFoundException;

import static ru.dosport.entities.Messages.*;

/**
 * Контроллер - глобальный обработчик исключений.
 */
@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());
        String message = String.format(INVALID_VALUE, "value", ex.getValue());
        return new ResponseEntity<>(new ErrorDto(400, message), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            stringBuilder.append("Параметр ").append(((FieldError) error).getField())
                    .append(" имеет неверное значение: ").append(error.getDefaultMessage()).append(". ");
        });
        log.error(stringBuilder.toString());
        return new ResponseEntity<>(new ErrorDto(400, stringBuilder.toString()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());
        FieldError fieldError = ex.getFieldError();
        String message = fieldError != null?
            String.format(INVALID_VALUE, fieldError.getField(), fieldError.getRejectedValue()) : BAD_REQUEST;
        return new ResponseEntity<>(new ErrorDto(400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

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
