package ru.dosport.entities;

import lombok.*;
import ru.dosport.enums.Infrastructure;
import ru.dosport.enums.MetroStation;
import ru.dosport.enums.SurfaceType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Город
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    // Адрес площадки
    @Column(name = "address", nullable = false, unique = true, length = 255)
    private String address;

    // Название площадки
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    // Широта
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    // Долгота
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    // Станция метро
    @Column(name = "metro_station")
    @Enumerated(EnumType.ORDINAL)
    private MetroStation metroStation;

    // Тип покрытия
    @Column(name = "surface_type")
    @Enumerated(EnumType.ORDINAL)
    private SurfaceType surfaceType;

    // Стоимость аренды в час
    @Column(name = "rent_price")
    private Integer rentPrice;

    // Является ли площадка открытой (расположена на улице)
    @Column(name = "opened", nullable = false)
    private Boolean opened;

    // Список инфраструктуры площадки
    @ManyToMany
    @JoinTable(
            name = "sportground_infrastructures",
            joinColumns = @JoinColumn(name = "sportground_id"),
            inverseJoinColumns = @JoinColumn(name = "infrastructure_id"))
    private Set<Infrastructure> infrastructures;

    // Список видов спорта
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "sportground_sport_types",
            joinColumns = @JoinColumn(name = "sportground_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_type_id"))
    private Set<SportType> sportTypes;

    // Список пользователей для которых площадка является избранной
    @ManyToMany
    @JoinTable(
            name = "user_sportgrounds",
            joinColumns = @JoinColumn(name = "sportground_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    // Список мероприятий на площадке
    @OneToMany(mappedBy = "sportGround")
    private List<Event> events;

    // Список отзывов о площадке
    @OneToMany(mappedBy = "sportGroundId")
    private List<Review> reviews;
}
