package ru.dosport.future;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dosport.entities.SportType;
import ru.dosport.entities.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность событие
 */
@Entity
@Getter
@Setter
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
    private SportType sportType;

    // Игровая площадка
    @ManyToMany
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    // Список участников этого мероприятия
    @OneToMany
    @JoinTable(name = "User_id")
    private List<User> users = new ArrayList<>();
}
