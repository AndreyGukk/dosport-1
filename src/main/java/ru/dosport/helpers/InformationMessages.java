package ru.dosport.helpers;

/**
 * Перечисление различных констант для информационных сообщений
 */
public final class InformationMessages {

    private InformationMessages() {}

    /**
     * Информационные сообщения
     */
    public static final String USER_WAS_FOUND = "Пользователь с логином %s загружен";
    public static final String SWAGGER_WAS_STARTED = "Swagger запущен";
    public static final String SUCCESSFUL_REQUEST = "Успешный запрос";
    public static final String USER_WAS_ACTIVATED = "Пользователь успешно активирован";

    /**
     * Сообщения об ошибках авторизации
     */
    public static final String USER_ALREADY_EXIST = "Пользователь с логином %s уже зарегистрирован";
    public static final String USER_NOT_FOUND_BY_USERNAME = "Пользователь с логином %s не найден";
    public static final String USER_NOT_FOUND_BY_ID = "Пользователь с индексом %s не найден";
    public static final String JWT_TOKEN_NOT_VALID = "Токен авторизации неверный либо истек его срок";
    public static final String BAD_CREDENTIALS = "Введен неверный логин или пароль";
    public static final String OLD_PASSWORD_INVALID = "Текущий пароль введен неверно";
    public static final String BAD_ACTIVATION_CODE = "Введен неверный код активации";

    /**
     * Иные сообщения об ошибках логики и полей запросов
     */
    public static final String ACCESS_DENIED = "Ошибка авторизации пользователя";
    public static final String BAD_REQUEST = "Запрос содержит ошибочные данные";
    public static final String WRONG_PATH = "Неверный путь запроса";
    public static final String INVALID_VALUE = "Значение поля %s неверное: %s";
    public static final String INVALID_USERNAME_LENGTH = "Никнейм должен содержать от 4 до 50 символов";
    public static final String DATA_NOT_BLANK = "Должно быть не пустым поле ";
    public static final String NUMBER_MUST_BE_POSITIVE = "Должно быть положительным значение поля ";
    public static final String PASSWORDS_MISMATCH = "Значения полей Пароль и Подтверждение пароля должны совпадать";
    public static final String INVALID_PASSWORD_LENGTH = "Пароль должен содержать от 6 до 25 символов";
    public static final String INVALID_EMAIL_LENGTH = "Адрес электронной почты должен содержать от 6 до 25 символов";
    public static final String INVALID_PASSWORD_CONFIRM_LENGTH =
            "Подтверждение пароля должно содержать от 6 до 25 символов";
    public static final String USER_SPORT_NOT_FOUND_BY_USER_AND_SPORT_TYPE =
            "Вид спорта c индексом %s для пользователя c индексом %s не найден";
    public static final String DATA_NOT_FOUND_BY_ID = "Данные c индексом %s не найдены";
    public static final String DATA_WAS_NOT_SAVED = "Данные не сохранены по запросу ";
    public static final String DATA_NOT_FOUND = "Данные не найдены ";
    public static final String DATE_MUST_BE_IN_FUTURE = "Допустимо только будущее время для параметра ";
    public static final String INVALID_DATE_TIME_FORMAT = "Неверный формат даты и времени параметра ";

    // Нарушение прав доступа
    public static final String MESSAGE_DOES_NOT_BELONG_TO_EVENT =
            "Сообщение c индексом %s не принадлежит событию с индексом %s";
    public static final String USER_IS_NOT_MESSAGE_AUTHOR =
            "Пользователь с индексом %s не является автором сообщения c индексом %s или администратором";
    public static final String USER_IS_NOT_EVENT_ORGANIZER =
            "Пользователь с индексом %s не является организатором мероприятия c индексом %s или администратором";
    public static final String CANNOT_PARTICIPATE_IN_PRIVATE_EVENT =
            "Нельзя принять участие в приватном мероприятии без приглашения";
    public static final String CANNOT_SUBSCRIBE_TO_MYSELF = "Нельзя подписаться на самого себя";

    // Отсутствие данных по индексу
    public static final String EVENT_NOT_FOUND_BY_ID = "Мероприятие c индексом %s не найдено";
    public static final String MESSAGE_NOT_FOUND_BY_ID = "Сообщение c индексом %s не найдено";
}
