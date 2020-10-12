package ru.dosport.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Исключение, выбрасываемое при ошибке авторизации.
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
