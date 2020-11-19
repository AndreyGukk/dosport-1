package ru.dosport.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность Отзыв о спортивной площадке
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Автор отзыва
    @Column(name = "user_id", nullable = false)
    private Long userId;

    //Полное имя автора
    @Column(name = "user_full_name", nullable = false)
    private String userFullName;

    // Площадка отзыва
    @Column(name = "sportground_id", nullable = false)
    private Long sportGroundId;

    // Дата отзыва
    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Текст отзыва
    @Column(name = "text", nullable = false)
    private String text;
}
