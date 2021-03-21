package ru.dosport.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Сущность Мероприятие
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Дата проведения мероприятия
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDateTime;

    // Время начала мероприятия
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDateTime;

    // Время завершения мероприятия
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDateTime;

    // Вид спорта
    @ManyToOne
    @JoinColumn(name = "sport_type_id", nullable = false)
    private SportType sportType;

    // Игровая площадка
    @ManyToOne
    @JoinColumn(name = "sportground_id", nullable = false)
    private SportGround sportGround;

    // Организатор мероприятия
    @Column(name = "organizer_id", nullable = false)
    private Long organizerId;

    // Описание мероприятия
    @Column(name = "description", length = 150)
    private String description;

    // Приватность мероприятия
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    // Цена участия в мероприятии
    @Column(name = "price")
    private Integer price;

    // Максимальное количество участников мероприятия
    @Column(name = "maximum_members")
    private Short maximumMembers;

    // Список участников мероприятия
    @OneToMany(mappedBy = "eventId")
    private Set<EventMember> members;
}
