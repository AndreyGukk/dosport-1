package ru.dosport.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.FetchType.EAGER;

/**
 * Сущность календарь пользователя
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "user_calendar")
public class User_Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // чей календарь
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    //список мероприятий
    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "event_id")// Внешний ключ
    private Map<LocalDate, Event> kindsOfSport = new HashMap<>();

}
