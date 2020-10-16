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
@Table(name = "sport_types")
public class SportType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Вид спорта
    @Column(name = "description", nullable = false, unique = true)
    private String description;

    // Дополнительные поля: количество человек, продолжительность игры, сезон, классификация по разделам
}
