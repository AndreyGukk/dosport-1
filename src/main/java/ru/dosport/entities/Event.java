package ru.dosport.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

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

    // Дата создания мероприятия
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
    @Column(name = "sportground_id", nullable = false)
    private Long sportGround;

    // Организатор мероприятия
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    // Описание мероприятия
    @Column(name = "description", length = 150)
    private String description;

    // Приватность мероприятия
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    // Цена участия в мероприятии
    @Column(name = "price")
    private Integer price;

    // Максимальное количество участников
    @Column(name = "maximum_users")
    private Short maximumUsers;

    // Текущее количество участников
    @Column(name = "users_amount")
    private Short usersAmount;

    // Текущее количество сообщений
    @Column(name = "messages_amount")
    private Short messagesAmount;

    // Список участников
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;

    // Список приглашенных
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_invitations",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> invitations;
}
