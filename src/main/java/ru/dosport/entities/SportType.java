package ru.dosport.entities;

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
    private Short id;

    // Вид спорта
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    public SportType(String title) {
        this.title = title;
    }
}
