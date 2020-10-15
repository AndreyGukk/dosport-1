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
@Table(name = "kindsOfSport")
public class KindOfSport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Вид спорта
    @Column(name = "kindOfSport", nullable = false, unique = true)
    private String kindOfSport;

    //возможные поля: количество человек, продолжительность игры, сезон
}
