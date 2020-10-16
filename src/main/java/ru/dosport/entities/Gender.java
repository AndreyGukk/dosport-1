package ru.dosport.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    NOTSELECTED ("Не выбран"),
    FEMALE("Женский"),
    MALE("Мужской");

    private final String description;
}
