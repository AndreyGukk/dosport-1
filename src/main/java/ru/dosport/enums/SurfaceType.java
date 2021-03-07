package ru.dosport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление типов покрытий спортивных площадок
 */
@Getter
@RequiredArgsConstructor
public enum SurfaceType {

    NOT_SELECTED    ("Не выбрано"),
    SOIL            ("Грунт"),
    SAND            ("Песок"),
    ASPHALT         ("Асфальт"),
    RUBBER          ("Резина"),
    ARTIFICIAL_LAWN ("Искуственный газон"),
    NATURAL_LAWN    ("Натуральный газон"),
    PARQUET         ("Паркет"),
    ICE             ("Лед"),
    HARD            ("Хард"),
    MATS            ("Маты"),
    POOL            ("Бассейн");

    private final String description;
}
