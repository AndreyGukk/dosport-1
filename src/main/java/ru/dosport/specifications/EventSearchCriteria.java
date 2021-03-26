package ru.dosport.specifications;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Критерии поиска мероприятий
 */
@Getter
@Builder
public class EventSearchCriteria {

    // Дата и время начала интервала создания
    private final LocalDateTime creationDateTimeFrom;

    // Дата и время конца интервала создания
    private final LocalDateTime creationDateTimeTo;

    // Время начала
    private final LocalDateTime startDateTimeFrom;

    // Время завершения
    private final LocalDateTime endDateTimeTo;

    // Идентификатор вида спорта
    private final Short sportTypeId;

    // Идентификатор игровой площадки
    private final Long sportGroundId;

    // Идентификатор организатора
    private final Long organizerId;

    // Приватность
    private final Boolean isPrivate;

    // Минимальная цена участия
    private final Integer minPrice;

    // Максимальная цена участия
    private final Integer maxPrice;
}
