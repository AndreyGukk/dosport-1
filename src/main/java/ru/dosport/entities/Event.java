package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность Мероприятие
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class Event {

    //TODO:
    // 1. раскомментировать и дописать поля сущности
    // 2. построить правильные соотношения полей сущностей с БД
    // 3. создать скрипт создания таблицы БД по образу имеющихся в resourses/db.migration

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Дата проведения мероприятия
    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Дата и время начала мероприятия
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    // Дата и время завершения мероприятия
    @Column(name = "end_time")
    private LocalDateTime endTime;

    // Вид спорта
    @ManyToOne
    @JoinColumn(name = "sport_type_id", nullable = false)
    private SportType sportType;

    // Игровая площадка
    @ManyToOne
    @JoinColumn(name = "sportground_id", nullable = false)
    private SportGround sportGround;

    // Организатор мероприятия
    @ManyToOne
    @JoinColumn(name = "organizer_user_id", nullable = false)
    private User organizer;

    // Список участников мероприятия
    @OneToMany
    @JoinTable(name = "event_member")
    private List<User> users = new ArrayList<>();

    // ID чата
    @Column(name = "chat_id")
    private Long idChat;
}
