package ru.dosport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Статус участия пользователя в мероприятии
 */
@Getter
@RequiredArgsConstructor
public enum ParticipationStatus {

    NOT_SELECTED("Не выбран"),
    MAYBE("Возможно пойду"),
    DEFINITELY("Точно пойду");

    private final String description;
}
