package ru.dosport.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность Виды спорта
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "sportType")
public class SportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Вид спорта
    @Column(name = "type", nullable = false, unique = true)
    private String type;

    //возможные поля: количество человек, продолжительность игры, сезон, классификация (танцы/игры/единоборства/экстрим)
}
