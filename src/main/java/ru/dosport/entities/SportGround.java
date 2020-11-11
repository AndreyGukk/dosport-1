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
    // 1. Список избраных площадок (или ещё таблица в этом модуле или перенести к пользователю - надо обдумать)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Адрес площадки
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    // Широта
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    // Долгота
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    // Название площадки
    @Column(name = "title", nullable = false)
    private String title;

    // Вид спорта
    @ManyToMany
    @JoinTable(
            name = "sportground_sport_types",
            joinColumns = @JoinColumn(name = "sportground_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_type_id"))
    private List<SportType> sportType;

    // Список мероприятий на площадке
    @OneToMany(mappedBy = "sportGround")
    private List<Event> events;

    // Список отзывов
    @OneToMany(mappedBy = "sportGround")
    private List<CommentSportGround> commentSportGrounds;
}
