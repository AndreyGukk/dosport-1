package ru.dosport.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "organizer_user_id", nullable = false)
    private Long organizerId;

    // Список участников мероприятия
    @OneToMany(mappedBy = "event")
    private Set<EventMember> members;

    // ID чата
    @Column(name = "chat_id")
    private Long chatId;
}
