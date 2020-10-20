package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность Мероприятие
 */
//@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(name = "event")
public class Event {

    //TODO:
    // 1. раскомментировать и дописать поля сущности
    // 2. построить правильные соотношения полей сущностей с БД
    // 3. создать скрипт создания таблицы БД по образу имеющихся в resourses/db.migration
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Дата и время начала мероприятия
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    // Дата и время завершения мероприятия
    @Column(name = "stop_date_time", nullable = false)
    private LocalDateTime stopDateTime;

    // Вид спорта
    @ManyToMany
    @JoinColumn(name = "sport_type_id", nullable = false)
    private SportType sportType;

    // Игровая площадка
    @ManyToMany
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    // Список участников мероприятия
    @OneToMany
    @JoinTable(name = "user_id")
    private List<User> users = new ArrayList<>();
    */
}
