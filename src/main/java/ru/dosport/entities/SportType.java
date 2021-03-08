package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность Вид спорта
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

    @ManyToMany
    @JoinTable(
            name = "sportground_sport_types",
            joinColumns = @JoinColumn(name = "sport_type_id"),
            inverseJoinColumns = @JoinColumn(name = "sportground_id"))
    private List<SportGround> sportGrounds;

    public SportType(String title) {
        this.title = title;
    }
}
