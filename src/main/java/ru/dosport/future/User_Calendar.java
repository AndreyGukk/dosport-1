package ru.dosport.future;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dosport.entities.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Сущность календарь пользователя
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_calendar")
public class User_Calendar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Пользователь - владелец календаря
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    //список мероприятий
    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "event_id")// Внешний ключ
    private Map<LocalDate, Event> kindsOfSport = new HashMap<>();
}
