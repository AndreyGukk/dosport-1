package ru.dosport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    NOT_SELECTED("Не выбран"),
    FEMALE("Женский"),
    MALE("Мужской");

    private final String description;
}
