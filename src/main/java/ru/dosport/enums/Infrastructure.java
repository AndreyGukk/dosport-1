package ru.dosport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление вариантов инфраструктуры спортивных площадок
 */
@Getter
@RequiredArgsConstructor
public enum Infrastructure {

    NOT_SELECTED    ("Не выбрано"),
    LOCKER_ROOM     ("Раздевалка"),
    PARKING         ("Парковка"),
    STANDS          ("Трибуны"),
    SHOWER          ("Душ"),
    LIGHTNING       ("Освещение"),
    STORAGE_ROOM    ("Камера хранения"),
    EQUIPMENT_RENTAL("Аренда оборудования"),
    SAUNA           ("Сауна");

    private final String description;
}
