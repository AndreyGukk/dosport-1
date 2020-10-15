package ru.dosport.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность событие
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Дата и время мероприятия
    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Продолжительность
    @Column(name = "duration", nullable = false)
    private Long duration;

    // Вид спорта
    @ManyToMany
    @JoinColumn(name = "kindOfSport_id")
    private KindOfSport kindOfSport;

    //площадка
    @ManyToMany
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    //список участников этого мероприятия
    @OneToMany
    @JoinTable(name = "User_id")
    private List<User> users = new ArrayList<>();


}
