package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность Спортивная площадка
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sportgrounds")
public class SportGround {

    //TODO:
    // 1. раскомментировать и дописать поля сущности
    // 2. построить правильные соотношения полей сущностей с БД
    // 3. создать скрипт создания таблицы БД по образу имеющихся в resourses/db.migration

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Адрес площадки
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    // Название площадки
    @Column(name = "title", nullable = false)
    private String title;

    // Вид спорта
    @ManyToOne
    @JoinColumn(name = "sport_type_id", nullable = false)
    private SportType sportType;

    // Список мероприятий на площадке
    @OneToMany(mappedBy = "sportGround")
    private List<Event> events;

    // Список отзовов
    @OneToMany(mappedBy = "sportGround")
    private List<CommentSportGround> commentSportGrounds;
}
