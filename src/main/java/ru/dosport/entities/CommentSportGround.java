package ru.dosport.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "comments_sportground")
public class CommentSportGround {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Автор отзыва
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Площадка отзыва
    @ManyToOne
    @JoinColumn(name = "sportground_id", nullable = false)
    private SportGround sportGround;

    // Дата отзыва
    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Текст отзыва
    @Column(name = "text", nullable = false)
    private String text;
}
