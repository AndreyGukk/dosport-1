package ru.dosport.helpers;

import static ru.dosport.helpers.Patterns.LOCAL_DATE_TIME_PATTERN;

/**
 * Класс, содержащий название и описание параметров для Swagger
 */
public final class SwaggerMessages {

    private SwaggerMessages () {}

    // Пользователи
    public static final String PAR_PAGE_NUMBER = "Номер страницы результатов поиска. По умолчанию - 0";
    public static final String PAR_USER_DTO = "Данные профиля пользователя";
    public static final String PAR_USER_REQUEST = "Запрос для регистрации нового Пользователя";
    public static final String PAR_USER_ID = "Идентификатор пользователя";
    public static final String PAR_USERNAME = "Никнейм пользователя";
    public static final String PAR_USER_PHOTO = "Ссылка на файл фото";
    public static final String PAR_EMAIL = "Адрес электронной почты";
    public static final String PAR_UUID = "Уникальный идентификатор UUID";

    // Мероприятия
    public static final String PAR_EVENT_DTO = "Данные мероприятия";
    public static final String PAR_EVENT_ID = "Идентификатор мероприятия";
    public static final String PAR_CREATION_DATE = "Дата создания";
    public static final String PAR_CREATION_DATE_FORMAT = "Дата создания в формате " + LOCAL_DATE_TIME_PATTERN;
    public static final String PAR_START_DATE = "Дата и время начала";
    public static final String PAR_START_DATE_FORMAT = "Дата и время начала в формате " + LOCAL_DATE_TIME_PATTERN;
    public static final String PAR_END_DATE = "Дата и время окончания";
    public static final String PAR_END_DATE_FORMAT = "Дата и время окончания в формате " + LOCAL_DATE_TIME_PATTERN;
    public static final String PAR_PRIVATE = "Приватность (закрытость)";
    public static final String PAR_ORGANIZER_ID = "Идентификатор пользователя-организатора";
    public static final String PAR_ORGANIZER_DTO = "Данные пользователя-организатора";
    public static final String PAR_PRICE = "Цена участия";
    public static final String PAR_PARTICIPANTS = "Список участников";
    public static final String PAR_EVENT_DESCRIPTION = "Описание мероприятия, до 150 символов";
    public static final String PAR_MAX_USERS = "Максимальное количество участников";
    public static final String PAR_USERS_COUNT = "Текущее количество участников";
    public static final String PAR_MESSAGES_COUNT = "Текущее количество сообщений";
    public static final String PAR_CREATION_DATE_FROM =
            "Начальная дата и время интервала создания в формате " + LOCAL_DATE_TIME_PATTERN;
    public static final String PAR_CREATION_DATE_TO =
            "Конечная дата и время интервала создания в формате " + LOCAL_DATE_TIME_PATTERN;
    public static final String PAR_PRICE_MIN = "Минимальная цена";
    public static final String PAR_PRICE_MAX = "Максимальная цена";

    // Сообщения
    public static final String PAR_MESSAGE_ID = "Идентификатор сообщения";
    public static final String PAR_MESSAGE_DTO = "Данные сообщения";
    public static final String PAR_MESSAGE_TEXT = "Текст оообщения";

    // Виды спорта
    public static final String PAR_SPORT_TYPE_ID = "Идентификатор вида спорта";
    public static final String PAR_SPORT_TYPE_NAME = "Название вида спорта";
    public static final String PAR_SPORT_TYPE_DTO = "Данные вида спорта";
    public static final String PAR_SPORT_TYPE_LIST = "Список данных видов спорта";

    // Спортивные площадки
    public static final String PAR_SPORTGROUND_ID = "Идентификатор спортивной площадки";
    public static final String PAR_SPORTGROUND_DTO = "Данные спортивной площадки";

    public static final String PAR__ID = "";
}
