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

    // Автор отзова
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    // Площадка отзова
    @ManyToOne
    @JoinColumn(name = "id_sportground", nullable = false)
    private SportGround sportGround;

    // Дата отзова
    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Текст отзова
    @Column(name = "text", nullable = false)
    private String text;
}
