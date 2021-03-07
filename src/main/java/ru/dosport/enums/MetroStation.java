package ru.dosport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечислений станций метро
 */
@Getter
@RequiredArgsConstructor
public enum MetroStation {

    NOT_SELECTED    ("Не выбрано"),
    SOKOLNIKI       ("Сокольники");

    private final String description;
}
