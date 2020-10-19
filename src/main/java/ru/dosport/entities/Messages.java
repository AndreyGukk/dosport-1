package ru.dosport.entities;

/**
 * Перечисление различных констант для информационных сообщений
 */
public final class Messages {

    private Messages() {
    }

    /**
     * Информационные сообщения
     */
    public static final String USER_WAS_CREATED = "Пользователь с адресом эл. почты %s создан";

    public static final String USER_WAS_FOUND = "Пользователь с адресом эл. почты %s загружен";

    public static final String SWAGGER_IS_INITIALIZING = "Инициализируется Swagger";

    public static final String SWAGGER_WAS_STARTED = "Swagger запущен";

    /**
     * Сообщения об ошибках авторизации
     */
    public static final String USER_HAS_ALREADY_CREATED = "Пользователь с адресом эл. почты %s уже зарегистрирован";

    public static final String USER_NOT_FOUND_BY_USERNAME = "Пользователь с адресом эл. почты %s не найден";

    public static final String USER_NOT_FOUND_BY_ID = "Пользователь с индексом %s не найден";

    public static final String JWT_TOKEN_NOT_VALID = "Токен авторизации неверный либо истек его срок";

    public static final String BAD_CREDENTIALS = "Введен неверный адрес эл. почты или пароль";

    public static final String OLD_PASSWORD_INVALID = "Текущий пароль введен неверно";

    /**
     * Иные сообщения об ошибках логики и полей запросов
     */
    public static final String ACCESS_DENIED = "Доступ в данный раздел запрещен";

    public static final String BAD_REQUEST = "Введены неверные данные";

    public static final String INVALID_VALUE = "Значение поля %s неверное: %s";

    public static final String INVALID_USERNAME_LENGTH = "Поле Адрес эл. почты должно содержать от 4 до 50 символов";

    public static final String USERNAME_NOT_BLANK = "Поле Адрес эл. почты должно быть не пустым";

    public static final String PASSWORD_NOT_BLANK = "Поле Пароль должно быть не пустым";

    public static final String INVALID_PASSWORD_LENGTH = "Поле Пароль должно содержать от 4 до 20 символов";

    public static final String PASSWORDS_MISMATCH = "Значения полей Пароль и Подтверждение пароля должны совпадать";

    public static final String FIRSTNAME_NOT_BLANK = "Поле Имя должно быть не пустым";

    public static final String USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE = "Вид спорта пользователя не найден";

}
