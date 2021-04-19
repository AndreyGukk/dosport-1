package ru.dosport.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Ошибка: регистрация пользователей этой соц. сети не поддерживается
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
