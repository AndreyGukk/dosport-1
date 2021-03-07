package ru.dosport.helpers;

/**
 * Перечисление различных констант для информационных сообщений
 */
public final class Messages {

    private Messages() {
    }

    /**
     * Информационные сообщения
     */
    public static final String USER_WAS_FOUND = "Пользователь с логином %s загружен";
    public static final String SWAGGER_WAS_STARTED = "Swagger запущен";
    public static final String SUCCESSFUL_REQUEST = "Успешный запрос";

    /**
     * Сообщения об ошибках авторизации
     */
    public static final String USER_ALREADY_EXIST = "Пользователь с логином %s уже зарегистрирован";
    public static final String USER_NOT_FOUND_BY_USERNAME = "Пользователь с логином %s не найден";
    public static final String USER_NOT_FOUND_BY_ID = "Пользователь с индексом %s не найден";
    public static final String JWT_TOKEN_NOT_VALID = "Токен авторизации неверный либо истек его срок";
    public static final String BAD_CREDENTIALS = "Введен неверный логин или пароль";
    public static final String OLD_PASSWORD_INVALID = "Текущий пароль введен неверно";

    /**
     * Иные сообщения об ошибках логики и полей запросов
     */
    public static final String ACCESS_DENIED = "Ошибка авторизации пользователя";
    public static final String BAD_REQUEST = "Запрос содержит ошибочные данные";
    public static final String INVALID_VALUE = "Значение поля %s неверное: %s";
    public static final String INVALID_USERNAME_LENGTH = "Поле Адрес эл. почты должно содержать от 4 до 50 символов";
    public static final String INVALID_FIRSTNAME_LENGTH = "Поле Имя должно содержать от 2 до 50 символов";
    public static final String INVALID_LASTNAME_LENGTH = "Поле Фамилия должно содержать от 2 до 100 символов";
    public static final String DATA_NOT_BLANK = "Должно быть не пустым поле ";
    public static final String PASSWORDS_MISMATCH = "Значения полей Пароль и Подтверждение пароля должны совпадать";
    public static final String INVALID_PASSWORD_LENGTH = "Поле Пароль должно содержать от 6 до 25 символов";
    public static final String INVALID_PASSWORD_CONFIRM_LENGTH =
            "Поле Подтверждение пароля должно содержать от 6 до 25 символов";
    public static final String USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE =
            "Вид спорта c индексом %s для пользователя c индексом %s не найден";

    public static final String DATA_NOT_FOUND_BY_ID = "Данные c индексом %s не найдены";
    public static final String DATA_WAS_NOT_SAVED = "Данные не сохранены по запросу ";
    public static final String DATA_NOT_FOUND = "Данные не найдены ";
}
