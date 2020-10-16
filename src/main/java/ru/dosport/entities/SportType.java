package ru.dosport.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность Виды спорта
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "sportType")
public class SportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Вид спорта
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    //возможные поля: количество человек, продолжительность игры, сезон, классификация (танцы/игры/единоборства/экстрим)
}
