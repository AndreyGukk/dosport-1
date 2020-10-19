package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Сущность календарь пользователя
 */
//@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(name = "user_calendar")
public class UserCalendar {

    //TODO:
    // 1. раскомментировать и дописать поля сущности
    // 2. построить правильные соотношения полей сущностей с БД
    // 3. создать скрипт создания таблицы БД по образу имеющихся в resourses/db.migration
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Пользователь - владелец календаря
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Список мероприятий
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_id")
    private Map<LocalDate, Event> kindsOfSport = new HashMap<>();
    */
}
