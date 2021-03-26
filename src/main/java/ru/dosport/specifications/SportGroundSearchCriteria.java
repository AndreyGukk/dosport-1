package ru.dosport.specifications;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * Критерии поиска спортивных площадок
 */
@Getter
@Builder
public class SportGroundSearchCriteria {

    // Город
    private final String city;

    // Адрес площадки
    private final String address;

    // Станция метро
    private final String metroStation;

    // Тип покрытия
    private final String surfaceType;

    // Начальная (минимальная) широта
    private final Double minLatitude;

    // Конечная (максимальная) широта
    private final Double maxLatitude;

    // Начальная (минимальная) долгота
    private final Double minLongitude;

    // Конечная (максимальная) долгота
    private final Double maxLongitude;

    // Минимальная стоимость аренды в час
    private final Integer minRentPrice;

    // Максимальная стоимость аренды в час
    private final Integer maxRentPrice;

    // Является ли площадка открытой
    private final Boolean opened;

    // Список инфраструктуры площадки
    private final Set<String> infrastructures;

    // Список видов спорта
    private final Set<String> sportTypes;
}
