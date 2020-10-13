package ru.dosport.entities;

/**
 * Перечисление различных констант для информационных сообщений
 */
public final class Messages {

    private Messages() {
    }

    public static final String PASSWORD_MISMATCH = "Пароли не совпадают";

    public static final String USER_CREATED = "Пользователь с логином %s успешно создан";

    public static final String USER_HAS_ALREADY_CREATED = "Пользователь с логином %s уже зарегистрирован";

    public static final String USER_NOT_FOUND = "Пользователь с именем: %s не найден";

    public static final String USER_FOUND = "Пользователь с именем: %s успешно загружен";

    public static final String ACCESS_DENIED = "Доступ в данный раздел запрещен";

    public static final String BAD_CREDENTIALS = "Неверное имя пользователя или пароль";

    public static final String JWT_TOKEN_NOT_VALID = "Токен авторизации неверный либо истек его срок";

    public static final String SWAGGER_IS_INITIALIZING = "Инициализируется Swagger";

    public static final String SWAGGER_WAS_STARTED = "Swagger запущен";

    public static final String BAD_REQUEST = "Введены неверные данные";
}
