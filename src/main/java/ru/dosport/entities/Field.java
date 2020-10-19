package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность Спортивная площадка
 */
//@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(name = "fields")
public class Field {

    //TODO:
    // 1. раскомментировать и дописать поля сущности
    // 2. построить правильные соотношения полей сущностей с БД
    // 3. создать скрипт создания таблицы БД по образу имеющихся в resourses/db.migration
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Адрес площадки
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    // Тип площадки
    @Column(name = "type", nullable = false, unique = true)
    private String type;
    */
}
