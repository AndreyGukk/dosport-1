package ru.dosport.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность Спортивная площадка
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sportgrounds")
public class SportGround {

    //TODO:
    // 1. Список избраных площадок

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
    @ManyToMany
    @JoinColumn(name = "sport_type_id", nullable = false)
    private List<SportType> sportType;

    // Список мероприятий на площадке
    @OneToMany(mappedBy = "sportGround")
    private List<Event> events;

    // Список отзовов
    @OneToMany(mappedBy = "sportGround")
    private List<CommentSportGround> commentSportGrounds;
}
